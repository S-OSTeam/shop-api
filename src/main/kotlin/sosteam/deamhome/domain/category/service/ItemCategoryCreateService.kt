package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.entity.ItemCategory
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
    private val sequenceGenerator: SequenceGenerator
) {

    suspend fun createCategory(request: ItemCategoryRequest): ItemCategoryResponse {
        val itemCategory = request.asDomain().apply {
            // 아이템 카테고리 sequence 생성
            publicId = sequenceGenerator.generateSequence(ItemCategory.SEQUENCE_NAME)
        }

        // request 에 parentPublicId 가 있으면 데이터베이스에 부모 카테고리가 있는지 확인
        itemCategory.parentPublicId?.let { parentPublicId ->
            val parentCategory = itemCategoryRepository.findByPublicId(parentPublicId)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            // 부모 카테고리의 최대 깊이 + 1 이 MAX_DEPTH 를 초과하면 예외 처리
            val depth = calcDepth(parentCategory) + 1
            if (depth > ItemCategory.MAX_DEPTH) {
                throw MaxDepthExceedException()
            }
        }

        val saveCategory = itemCategoryRepository.save(itemCategory).awaitSingle()

        return ItemCategoryResponse.fromItemCategory(saveCategory)
    }

    // 상위 카테고리를 탐색해서 depth 를 반환하는 함수
    private suspend fun calcDepth(category: ItemCategory): Int {
        // 최상위 카테고리라면 0 을 반환
        return if (category.parentPublicId == null) {
            0
        } else {
            // 부모 카테고리의 depth + 1 을 반환
            val parentCategory = itemCategoryRepository.findByPublicId(category.parentPublicId!!)
                ?: throw CategoryNotFoundException(message = "상위 카테고리를 찾을 수 없습니다.")
            calcDepth(parentCategory) + 1
        }
    }

}
