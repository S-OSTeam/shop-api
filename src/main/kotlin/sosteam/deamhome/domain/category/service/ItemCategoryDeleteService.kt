package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
@Transactional
class ItemCategoryDeleteService (
    private val itemCategoryRepository: ItemCategoryRepository
) {
    //TODO 카테고리에 item 이 들어있으면 쓰레기통으로 옮기기
    //TODO 제일 아래에 있는 카테고리가 아니면 삭제 못하게 하기
    suspend fun deleteItemCategoryByPublicId(publicId: Long): String {
        val itemCategory = (itemCategoryRepository.deleteByPublicId(publicId)
            ?: throw CategoryNotFoundException())

        return itemCategory.title
    }

}
