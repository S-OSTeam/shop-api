package sosteam.deamhome.domain.item.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDate
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
	var stockCnt:Int = 0,
	@Column("avg_review")
	val avgReview: Double = 0.0,
	@Column("review_cnt")
	val reviewCnt: Int = 0,
	// 리뷰 점수 1점 ~ 5점
	@Column("review_score")
	val reviewScore: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0),
	@Column("qna_cnt")
	val qnaCnt: Int = 0,
	@Column("status")
	val status: Boolean = false,
	@Column("seller_id")
	val sellerId: String,
	@Column("free_delivery")
	val freeDelivery: Boolean = false,
	// 옵션 선택
	@Column("option")
	val option: ArrayList<String> = arrayListOf(),
	// 상품번호
	@Column("product_number")
	val productNumber: String,
	// 마감일
	@Column("deadline")
	val deadline: OffsetDateTime,
	// 원작
	@Column("original_work")
	val originalWork: String,
	// 재질
	@Column("material")
	val material: String,
	// 크기
	@Column("size")
	val size: String,
	// 무게
	@Column("weight")
	val weight: String,
	// 배송비
	@Column("shipping_cost")
	val shippingCost: Int,
) : BaseEntity(){
	@Column("image_urls")
	var imageUrls: MutableList<String> = mutableListOf()
}
