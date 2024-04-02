package sosteam.deamhome.domain.order.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("ordered_item")
class OrderedItem (
    @Id
    var id: Long?,
    @Column("order_id")
    val orderId: String,
    @Column("item_id")
    val itemId: String,
    @Column("count")
    val count: Int,

    // Todo : 아이템 옵션 추가해야함
    // Todo: 아이템별 쿠폰적용여부 로직 몰라서 추가 안함

): BaseEntity() {

}