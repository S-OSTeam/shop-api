package sosteam.deamhome.domain.account.handler.response

import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.image.entity.Image

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
        fun fromItem(item: Item, cartCnt: Int, check: Boolean) : CartResponse {
            return CartResponse(
                title = item.title,
                content = item.content,
                price = item.price,
//                image = item.images[0].fileUrl,
                image = item.imageUrls[0],
//                itemId = item.id,
                itemId = "item.id",
                cnt = cartCnt,
                check = check,
            )
        }
    }
}