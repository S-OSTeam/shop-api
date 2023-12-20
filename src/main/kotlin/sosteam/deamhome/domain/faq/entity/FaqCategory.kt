package sosteam.deamhome.domain.faq.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity

@Table("faq_category")
class FaqCategory(
	override var title: String,
	override var publicId: String,
	@Column("parent_public_id")
	override var parentPublicId: String,
	@Column("store_id")
	var storeId: String,
) : CategoryEntity(2) {
	@Id
	var id: Long? = null
}
