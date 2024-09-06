package sosteam.deamhome.domain.item.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.entity.BaseEntity
import java.time.OffsetDateTime

@Table("item")
data class Item(
	@Id
	@Column("id")
	var id: Long?,
	// unique column
	@Column("public_id")
	var publicId: String,
	@Column("category_public_id")
	var categoryPublicId: String,
	@Column("title")
	var title: String,
	@Column("content")
	var content: String,
	@Column("summary")
	var summary: String,
	@Column("price")
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
	// 리뷰 점수 1점 ~ 5점
	@Column("review_score")
	var reviewScore: List<Int> = listOf(0, 0, 0, 0, 0),
	@Column("qna_cnt")
	var qnaCnt: Int = 0,
	@Column("status")
	var status: ItemStatus,
	@Column("store_id")
	var storeId: String,
	@Column("free_delivery")
	var freeDelivery: Boolean = false,
	// 옵션 선택
	@Column("option")
	var option: List<String> = listOf(),
	// 상품번호
	@Column("product_number")
	var productNumber: String,
	// 마감일
	@Column("deadline")
	var deadline: OffsetDateTime,
	// 원작
	@Column("original_work")
	var originalWork: String,
	// 재질
	@Column("material")
	var material: String,
	// 크기
	@Column("size")
	var size: String,
	// 무게
	@Column("weight")
	var weight: String,
	// 배송비
	@Column("shipping_cost")
	var shippingCost: Int,
	@Column("image_urls")
	var imageUrls: List<String> = listOf()
) : BaseEntity() {
}
