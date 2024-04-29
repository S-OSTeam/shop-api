package sosteam.deamhome.domain.order.handler.response

import sosteam.deamhome.domain.item.entity.Item

data class OrderedItemResponse (
    val title: String,
    val content: String,
    val price: Int = 0,
    val image: String,
    val itemId: String,
    val cnt: Int,
){
    companion object{
        fun fromItem(item: Item?, cnt:Int) : OrderedItemResponse{
            return OrderedItemResponse(
                title = item?.title.orEmpty(),
                content = item?.content.orEmpty(),
                price = item?.price ?: 0,
                image = item?.imageUrls?.get(0).orEmpty(),
                itemId = item?.publicId.orEmpty(),
                cnt = cnt,
            )
        }
    }

}