package sosteam.deamhome.domain.review.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("review")
class Review(
	@Id
	var id: Long?,
	var title: String,
	@Column("public_id")
	var publicId: String,
	@Column("parent_public_id")
	var parentPublicId: String,
	var content: String,
	var score: Int = 0,
	var status: Boolean = false,
	@Column("user_id")
	val userId: String,
	@Column("item_id")
	val itemId: String,
	@Column("image_urls")
	var imageUrls: MutableList<String>,
	@Column("like_users")
	var likeUsers: MutableList<String>,
	@Column("purchase_options")
	var purchaseOptions: MutableList<String>,
	@Column("report_users")
	var reportUsers: MutableList<String>,
	@Column("report_contents")
	var reportContents: MutableList<String>
) : LogEntity()
