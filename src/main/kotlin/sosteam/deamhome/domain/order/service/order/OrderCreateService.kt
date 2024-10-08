package sosteam.deamhome.domain.order.service.order

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.cart.entity.CartItem
import sosteam.deamhome.domain.cart.exception.CartItemExceedsStockException
import sosteam.deamhome.domain.cart.repository.CartRepository
import sosteam.deamhome.domain.cart.service.CartDeleteService
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.domain.order.entity.OrderedItem
import sosteam.deamhome.domain.order.exception.OrderEmptyException
import sosteam.deamhome.domain.order.handler.request.OrderRequest
import sosteam.deamhome.domain.order.handler.response.OrderInfoResponse
import sosteam.deamhome.domain.order.repository.OrderRepository
import sosteam.deamhome.domain.order.repository.OrderedItemRepository

@Service
class OrderCreateService(
	private val orderRepository: OrderRepository,
	private val orderedItemRepository: OrderedItemRepository,
	private val cartRepository: CartRepository,
	private val itemRepository: ItemRepository,
	private val cartDeleteService: CartDeleteService,
) {
	//현재 사용자 장바구니 기준으로 주문 생성
	@Transactional
	suspend fun createOrder(request: OrderRequest, userId: String): OrderInfoResponse {
		val order: Order = request.asDomain()
		
		// 장바구니 아이템
		val cartItems: List<CartItem> = cartRepository.findByUserId(userId).toList()
		
		// 주문 상품 없음
		if (cartItems.isEmpty()) {
			throw OrderEmptyException()
		}
		
		// 주문 아이템 저장
		saveOrderedItem(cartItems, order)
		
		// 장바구니 주문 아이템 제거
		cartDeleteService.deleteCheckedItem(userId)
		
		val saved = orderRepository.save(order)
		return OrderInfoResponse.fromOrder(saved)
	}
	
	private suspend fun saveOrderedItem(cartItems: List<CartItem>, order: Order) {
		
		for (cartItem in cartItems) {
			if (cartItem.isCheck) { // 체크 돼있을 시
				val item: Item = itemRepository.findByPublicId(cartItem.itemId)
					?: throw ItemNotFoundException()
				
				if (item.stockCnt < cartItem.cnt) { // 재고부족
					throw CartItemExceedsStockException(item = item)
				} else { // 재고수량 반영
					item.stockCnt -= cartItem.cnt
					itemRepository.save(item)
				}
				// 주문 가격 계산
				// Todo: totalPrice 일단 물건 순 가격 합으로 계산함 ( 쿠폰이랑 포인트 포함 계산 가격으로 하는지 모르겠음)
				order.totalPrice += item.price * cartItem.cnt
				
				val orderedItem: OrderedItem = cartItem.asOrderedItem(order.publicId)
				orderedItemRepository.save(orderedItem)
			}
		}
	}
}