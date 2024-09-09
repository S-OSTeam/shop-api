package sosteam.deamhome.domain.question.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity

@Table("question_category")
class QuestionCategory(
	@Id
	var id: Long? = null,
	override var title: String,
	@Column("public_id")
	override var publicId: String,
	@Column("parent_public_id")
	override var parentPublicId: String,
	@Column("store_id")
	var storeId: String,
	@Column("post_id")
	var postId: String,
	@Column("item_id")
	var itemId: String,
	@Column("question_type")
	var questionType: QuestionType
) : CategoryEntity(2)
