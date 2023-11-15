package sosteam.deamhome.domain.item.entity.dto

import sosteam.deamhome.domain.account.entity.Account


class ItemDTO(
    val title: String? = null,
    val content: String? = null,
    val summary: String? = null,
    val price: Int = 0,
    val sellCnt: Int = 0,
    val wishCnt: Int = 0,
    val clickCnt: Int = 0,
    val avgReview: Double = 0.0,
    val reviewCnt: Int = 0,
    val qnaCnt: Int = 0,
    val status: Boolean = false,
    val images: List<String> = listOf(),
    val sellerId: String
//    val account: Account? = null
)