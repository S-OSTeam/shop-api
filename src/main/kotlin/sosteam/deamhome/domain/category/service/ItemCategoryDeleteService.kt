package sosteam.deamhome.domain.category.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.category.exception.CategoryDeleteFailException
import sosteam.deamhome.global.category.exception.CategoryNotFoundException

@Service
@Transactional
class ItemCategoryDeleteService (
    private val itemCategoryRepository: ItemCategoryRepository,
    private val itemRepository: ItemRepository,
) {
    suspend fun deleteItemCategoryByPublicId(publicId: String): Long {
        // 삭제할 카테고리가 존재하는지 확인
        itemCategoryRepository.findByPublicId(publicId)
            ?: throw CategoryNotFoundException()

        // 카테고리 하위에 카테고리가 있으면 삭제 불가
        val childrenCategories = itemCategoryRepository.findByParentPublicId(publicId).toList()
            .filterNot { it.isTop() }
        if (childrenCategories.isNotEmpty())
            throw CategoryDeleteFailException("삭제하려는 카테고리 하위에 다른 카테고리가 있습니다.")

        // 카테고리 하위에 아이템이 있으면 삭제 불가
        val childrenItems = itemRepository.findByCategoryPublicId(publicId).toList()
        if (childrenItems.isNotEmpty())
            throw CategoryDeleteFailException("삭제하려는 카테고리 하위에 아이템이 있습니다.")

        return itemCategoryRepository.deleteByPublicId(publicId)
    }

}
