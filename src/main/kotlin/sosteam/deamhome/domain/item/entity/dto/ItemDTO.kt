package sosteam.deamhome.domain.item.entity.dto

import sosteam.deamhome.domain.item.entity.Item

class ItemDTO(
    val title: String? = null,
    val price: Int? = null,
    val category: String? = null
) {
    companion object{
        fun fromItem(item: Item) : ItemDTO{
            return ItemDTO(
                title = item.title,
                price = item.price
            )
        }
    }
}