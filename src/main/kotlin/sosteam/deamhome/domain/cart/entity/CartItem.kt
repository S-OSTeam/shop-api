package sosteam.deamhome.domain.cart.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.order.entity.OrderedItem
import sosteam.deamhome.global.entity.BaseEntity

@Table("cart_item")
data class CartItem(
	@Id
	var id: Long?,
	@Column("item_id")
	val itemId: String,
	@Column("user_id")
	val userId: String,
	var cnt: Int,
	@Column("is_check")
	var isCheck: Boolean,
) : BaseEntity() {
	fun asOrderedItem(orderId: String): OrderedItem {
		return OrderedItem(
			id = null,
			orderId = orderId,
			itemId = itemId,
			count = cnt,
		)
	}
}
