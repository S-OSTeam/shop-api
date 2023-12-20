package sosteam.deamhome.domain.category.dto.response

import sosteam.deamhome.domain.category.entity.ItemCategory

class ItemCategoryResponse (
    val title: String,
    val sequence: Long,
    val parentSeq: Long?,
    val childrenSeq: List<Long>
) {
    companion object {
        fun fromItemCategory(itemCategory: ItemCategory): ItemCategoryResponse {
            return ItemCategoryResponse(
                title = itemCategory.title,
                sequence = itemCategory.sequence,
                parentSeq = itemCategory.parentSeq,
                childrenSeq = itemCategory.childrenSeq
            )
        }
    }
}