package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.global.attribute.Direction
import sosteam.deamhome.global.attribute.ItemSortCriteria

data class ItemSearchRequest(
    // publicId 로 검색
    val publicId: String?,
    // 카테고리 publicId 로 검색
    val categoryPublicId: String?,
    // 제목 포함된 상품 검색
    val title: String?,
    // 정렬 기준
    val sort: ItemSortCriteria?,
    // 페이지 번호
    val pageNumber: Long = 1,
    // 페이지 크기
    val pageSize: Long = 10,
    // 오름차순, 내림차순
    val direction: Direction?
) {
}