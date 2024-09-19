package sosteam.deamhome.domain.question.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.PostType
import sosteam.deamhome.global.entity.BaseEntity

@Table("question")
data class Question(
	@Id
	@Column("id")
	var id: Long?,
	@Column("title")
	var title: String,
	@Column("content")
	var content: String,
	@Column("summary")
	var summary: String?,
	@Column("user_id")
	var userId: String,
	@Column("public_id")
	var publicId: String,
	@Column("category_public_id")
	var categoryPublicId: String,
	@Column("post_id")
	var postId: String?,
	@Column("item_id")
	var itemId: String?,
	@Column("store_id")
	var storeId: String?,
	@Column("question_type")
	var questionType: QuestionType,
	@Column("question_status")
	var questionStatus: QuestionStatus,
	@Column("question_is_completed")
	var questionIsCompleted: Boolean,
	@Column("post_type")
	var postType: PostType,
	@Column("image_urls")
	var imageUrls: List<String> = listOf()
) : BaseEntity()