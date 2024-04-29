package sosteam.deamhome.domain.cart.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.order.entity.OrderedItem
import sosteam.deamhome.global.entity.BaseEntity

@Table("cart_item")
data class CartItem(
    @Id
    var id: Long?,
    val itemId: String,
    val userId: String,
    var cnt: Int,
    var checked: Boolean,
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
