package sosteam.deamhome.domain.item.resolver.request

data class ItemCreateRequest(
    val title: String,
    val content: String,
    val summary: String,
    val price: Int = 0,
    val sellCnt: Int = 0,
    val wishCnt: Int = 0,
    val clickCnt: Int = 0,
    val avgReview: Double = 0.0,
    val reviewCnt: Int = 0,
    val qnaCnt: Int = 0,
    val status: Boolean = false,
    val accountId: String,
    val itemCategoryId: String,
    val itemDetailCategoryId: String,
    val imageUrls: List<String>
)