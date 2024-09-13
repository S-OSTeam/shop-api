package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.global.attribute.Direction

data class QuestionSearchRequest(
	val questionFilter: QuestionFilter?,
	val questionCategoryFilter: QuestionCategoryFilter?,
	// publicId 로 검색
	val publicId: String?,
	// 카테고리 publicId 로 검색
	val categoryPublicId: String?,
	// 제목 포함된 상품 검색
	val title: String?,
	// 페이지 번호
	val pageNumber: Long = 1,
	// 페이지 크기
	val pageSize: Long = 10,
	// 오름차순, 내림차순
	val direction: Direction?
)

data class QuestionFilter(
	val title: String?,
	val content: String?,
	val publicId: String?
)

data class QuestionCategoryFilter(
	val title: String?,
	val parentPublicId: String?,
	val publicId: String?
)