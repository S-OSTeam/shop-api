package sosteam.deamhome.domain.cart.handler.response

import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.item.entity.Item

// Todo: 추가할 정보 있다면 추가
data class CartResponse(
    val title: String,
    val content: String,
    val price: Int = 0,
    val image: String,
    val itemId: String,
    val cnt: Int,
    val check: Boolean,
) {
    companion object{
        fun fromItem(item: Item?, cnt : Int, checked: Boolean) : CartResponse {
            return CartResponse(
                title = item?.title.orEmpty(),
                content = item?.content.orEmpty(),
                price = item?.price ?: 0,
                image = item?.imageUrls?.get(0).orEmpty(),
                itemId = item?.publicId.orEmpty(),
                cnt = cnt,
                check = checked,
            )
        }
    }
}