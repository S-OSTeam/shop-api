package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
import sosteam.deamhome.domain.category.exception.AlreadyExistCategoryException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.category.handler.request.ItemCategoryRequest
import sosteam.deamhome.domain.category.handler.response.ItemCategoryResponse
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.sequence.provider.SequenceGenerator

@Service
@Transactional
class ItemCategoryCreateService(
    private val itemCategoryRepository: ItemCategoryRepository,
    private val sequenceGenerator: SequenceGenerator,
    @Value("\${item.category.sequence.name}")
    private val sequenceName: String,
    @Value("\${item.category.max.depth}")
    private val maxDepth: Int
) {

    suspend fun createCategory(request: ItemCategoryRequest): ItemCategoryResponse {
        val itemCategory = request.asDomain().apply {
            // 아이템 카테고리 sequence 생성
            publicId = sequenceGenerator.generateSequence(sequenceName)
            // parentPublicId가 0L이면 자기 자신으로 설정
            parentPublicId = if (parentPublicId == 0L) publicId else parentPublicId
        }

        // 같은 부모에 중복된 이름의 카테고리가 있거나 최대 깊이를 초과하는지 확인
        request.parentPublicId?.let { validateParentCategory(it, request.title) }
            ?: validateTopCategories(request.title)

        val saveCategory = itemCategoryRepository.save(itemCategory).awaitSingle()

        return ItemCategoryResponse.fromItemCategory(saveCategory)
    }

    // 상위 카테고리를 탐색해서 depth 를 반환하는 함수
    private suspend fun calcDepth(category: ItemCategory): Int {
        // 최상위 카테고리라면 0 을 반환
        return if (category.isTop()) {
            0
        } else {
            // 부모 카테고리의 depth + 1 을 반환
            val parentCategory = itemCategoryRepository.findByPublicId(category.parentPublicId)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            calcDepth(parentCategory) + 1
        }
    }

    private suspend fun validateParentCategory(parentPublicId: Long, title: String) {
        val parentCategory = itemCategoryRepository.findByPublicId(parentPublicId)
            ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
        // 부모 카테고리의 최대 깊이 + 1 이 MAX_DEPTH 를 초과하면 예외 처리
        val depth = calcDepth(parentCategory) + 1
        if (depth > maxDepth) {
            throw MaxDepthExceedException()
        }
        // 같은 부모를 가진 카테고리에 이름이 중복된 카테고리가 있으면 예외처리
        if (itemCategoryRepository.findByParentPublicIdAndTitle(parentPublicId, title) != null)
            throw AlreadyExistCategoryException()
    }

    private suspend fun validateTopCategories(title: String) {
        // 최상위 카테고리에 같은 이름의 카테고리가 있으면 예외처리
        if (itemCategoryRepository.findAllItemCategoriesByTitle(title).toList().any { it.isTop() })
            throw AlreadyExistCategoryException()
    }

}
