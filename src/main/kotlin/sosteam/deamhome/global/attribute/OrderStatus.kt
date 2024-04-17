package sosteam.deamhome.global.attribute

//주문 확인중, 주문 취소, 배송 대기중, 배송중, 배송 완료, 환불, 교환, 반품
enum class OrderStatus {
	PROCESSING, CANCELLED, PENDING, TRANSIT, DELIVERED, REFUND, EXCHANGE, RETURN
}