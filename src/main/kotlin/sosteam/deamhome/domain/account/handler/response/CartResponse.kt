package sosteam.deamhome.domain.account.handler.response

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
        fun fromItem(item: Item, cartCnt: Int, check: Boolean) : CartResponse {
            return CartResponse(
                title = item.title,
                content = item.content,
                price = item.price,
                image = item.imageUrls[0],
                itemId = item.publicId,
                cnt = cartCnt,
                check = check,
            )
        }
    }
}