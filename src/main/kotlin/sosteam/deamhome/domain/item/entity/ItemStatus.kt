package sosteam.deamhome.domain.item.entity

// 예약상품, 판매 중단, 삭제됨, 매진, 판매 중
enum class ItemStatus {
	RESERVED, SUSPENDED, DELETED, SOLDOUT, AVAILABLE
}