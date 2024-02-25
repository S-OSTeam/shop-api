package sosteam.deamhome.domain.item.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("item")
data class Item(
	@Id
	var id: Long?,
	// unique column
	@Column("public_id")
	var publicId: String,
	@Column("category_public_id")
	var categoryPublicId: String,
	var title: String,
	var content: String,
	var summary: String,
	var price: Int = 0,
	@Column("sell_cnt")
	var sellCnt: Int = 0,
	@Column("wish_cnt")
	var wishCnt: Int = 0,
	@Column("click_cnt")
	var clickCnt: Int = 0,
	@Column("stock_cnt")
	var stockCnt: Int = 0,
	@Column("avg_review")
	var avgReview: Double = 0.0,
	@Column("review_cnt")
	var reviewCnt: Int = 0,
	@Column("qna_cnt")
	val qnaCnt: Int = 0,
	val status: Boolean = false,
	//account 의 userId 는 절대로 안바뀌겠지???
	@Column("seller_id")
	val sellerId: String,
	@Column("free_delivery")
	val freeDelivery: Boolean = false

) : BaseEntity() {
	@Column("image_urls")
	var imageUrls: MutableList<String> = mutableListOf()
	
}
