package sosteam.deamhome.domain.question.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("question")
class Question(
	var title: String,
	var content: String,
	@Column("user_id")
	var userId: String,
	@Column("public_id")
	var publicId: String,
	@Column("category_public_id")
	var categoryPublicId: String,
	@Column("question_id")
	var questionId: String?,
	@Column("store_id")
	var storeId: String?,
	@Column("question_type")
	var questionType: QuestionType,
	@Column("question_status")
	var questionStatus: QuestionStatus,
	@Column("image_urls")
	var imageUrls: MutableList<String> = mutableListOf()
) : BaseEntity() {
	@Id
	var id: Long? = null
}