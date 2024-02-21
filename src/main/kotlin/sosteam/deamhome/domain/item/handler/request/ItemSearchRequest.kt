package sosteam.deamhome.domain.item.handler.request

import java.util.*

data class ItemSearchRequest(
    var publicId: UUID?,
    // 카테고리 publicId 로 검색
    var categoryPublicId: UUID?,
    // 제목 포함된 상품
    var title: String?,
    // 최소 가격
    var minPrice: Int?,
    // 최대 가격
    var maxPrice: Int?,
    // 판매 수 높은 순
    var sellCnt: Int?,
    // 찜 많은 순
    var wishCnt: Int?,
    // 조회 수 높은 순
    var clickCnt: Int?,
    // 리뷰 점수 높은 순
    val avgReview: Double?,
    // 리뷰 많은 순
    val reviewCnt: Int?,
    // 무료 배송 여부
    val freeDelivery: Boolean = false
) {
}