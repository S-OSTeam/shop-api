package sosteam.deamhome.domain.category.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity

@Table("item_category")
data class ItemCategory(
    @Id
    var id: Long?,
    override var title: String,
    // unique column
    override var publicId: String,
    override var parentPublicId: String
) : CategoryEntity(2)
