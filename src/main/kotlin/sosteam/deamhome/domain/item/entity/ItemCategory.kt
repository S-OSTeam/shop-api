package sosteam.deamhome.domain.item.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity

@Table("item_category")
data class ItemCategory(
	@Id
	@Column("id")
	var id: Long?,
	@Column("title")
	override var title: String,
	// unique column
	@Column("public_id")
	override var publicId: String,
	@Column("parent_public_id")
	override var parentPublicId: String
) : CategoryEntity(2)
