package sosteam.deamhome.domain.category.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity

@Table("item_category")
data class ItemCategory(
	@Id
	var id: Long?,
	override var title: String,
	// unique column
	@Column("public_id")
	override var publicId: String,
	@Column("parent_public_id")
	override var parentPublicId: String
) : CategoryEntity(2)
