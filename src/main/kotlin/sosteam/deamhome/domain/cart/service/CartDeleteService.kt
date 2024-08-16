package sosteam.deamhome.domain.cart.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.cart.exception.CartItemNotFoundException
import sosteam.deamhome.domain.cart.repository.CartRepository

@Service
class CartDeleteService(
	private val cartRepository: CartRepository,
) {
	suspend fun deleteCartItem(userId: String, itemId: String): String {
		
		val removedCartItem = cartRepository.findByUserIdAndItemId(userId, itemId)
		
		// 삭제하고자 하는 아이템 없음
		if (removedCartItem == null) {
			throw CartItemNotFoundException()
		}
		
		cartRepository.delete(removedCartItem)
		return itemId
	}
	
	// order 후 체크된 아이템 제거
	suspend fun deleteCheckedItem(userId: String) {
		val cartItems = cartRepository.findByUserId(userId).toList()
		
		for (cartItem in cartItems) {
			if (cartItem.isCheck) {
				cartRepository.delete(cartItem)
			}
		}
	}
}
