package sosteam.deamhome.global.category.provider

import kotlinx.coroutines.flow.toList
import sosteam.deamhome.global.category.exception.AlreadyExistCategoryException
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.category.entity.CategoryEntity
import sosteam.deamhome.global.category.factory.CategoryProviderFactory
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse
import sosteam.deamhome.global.category.respository.CategoryRepository

class CategoryProvider<T: CategoryEntity> (
        factory: CategoryProviderFactory,
        clazz: Class<T>
) {
    private val repository: CategoryRepository<T> = factory.getRepository(clazz)

    suspend fun saveCategory(category: T): T {
        return repository.save(category)
    }

    // 상위 카테고리를 탐색해서 depth 를 반환하는 함수
    suspend fun calcDepth(category: T): Int {
        // 최상위 카테고리라면 0 을 반환
        return if (category.isTop()) {
            0
        } else {
            // 부모 카테고리의 depth + 1 을 반환
            val parentCategory = repository.findByPublicId(category.parentPublicId)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            calcDepth(parentCategory) + 1
        }
    }

    suspend fun validateCategory(category: T) {
        if (category.isTop()) {
            // 최상위 카테고리이면 title 로만 검증함
            validateTopCategory(category.title)
        } else {
            // 최상위 카테고리가 아니라면 depth 와 title 로 검증함
            validateParentCategory(category.parentPublicId, category.title, category.maxDepth)
        }
    }

    suspend fun validateParentCategory(parentPublicId: String, title: String, maxDepth: Int): Int {
        val parentCategory = repository.findByPublicId(parentPublicId)
            ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
        // 부모 카테고리의 깊이 + 1 이 MAX_DEPTH 를 초과하면 예외 처리
        val depth = calcDepth(parentCategory) + 1
        if (depth > maxDepth) {
            throw MaxDepthExceedException()
        }
        // 같은 부모를 가진 카테고리에 이름이 중복된 카테고리가 있으면 예외처리
        if (repository.findByParentPublicIdAndTitle(parentPublicId, title) != null)
            throw AlreadyExistCategoryException()
        return depth
    }

    suspend fun validateTopCategory(title: String) {
        // 최상위 카테고리에 같은 이름의 카테고리가 있으면 예외처리
        if (repository.findByTitle(title).toList().any { it.isTop() })
            throw AlreadyExistCategoryException()
    }

    // 모든 아이템 카테고리를 tree 형식으로 반환
    suspend fun findAllCategoriesTree(fromCategory: (T) -> (CategoryTreeResponse<T>)): List<CategoryTreeResponse<T>> {
        val categories = repository.findAll().toList()

        val map = mutableMapOf<String, CategoryTreeResponse<T>>()

        // key 는 publicId, value 는 CategoryTreeResponse 로 하는 map
        categories.forEach { category ->
            map[category.publicId] = fromCategory(category)
        }

        // 부모 카테고리의 children 에 자식 카테고리 추가
        categories
            .filterNot(CategoryEntity::isTop)
            .forEach { category ->
                val parent = map[category.parentPublicId]
                val child = map[category.publicId]
                parent!!.children.add(child!!)
            }

        // 최상위 카테고리만 모아서 list 로 반환
        val roots = categories
            .filter { it.isTop() }
            .map { map[it.publicId]!! }.toList()

        return roots
    }



    suspend fun findAllSubCategoriesTree(categoryId: String, fromCategory: (T) -> CategoryTreeResponse<T>): CategoryTreeResponse<T> {
        val allCategories = repository.findAll().toList()  // 모든 카테고리를 불러옴
        val map = mutableMapOf<String, CategoryTreeResponse<T>>()  // 카테고리 ID를 키로 하는 맵

        // 모든 카테고리를 맵에 추가
        allCategories.forEach { category ->
            map[category.publicId] = fromCategory(category)
        }

        // 카테고리 간의 부모-자식 관계 설정
        allCategories.forEach { category ->
            val parent = map[category.parentPublicId]
            val child = map[category.publicId]
            parent?.children?.add(child!!)
        }

        // 시작 카테고리의 자식만을 추출하여 반환
        return map[categoryId] ?: throw CategoryNotFoundException("Cannot find category with ID: $categoryId")
    }



}