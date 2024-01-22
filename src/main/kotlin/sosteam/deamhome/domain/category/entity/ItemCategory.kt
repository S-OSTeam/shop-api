package sosteam.deamhome.domain.category.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("item_category")
data class ItemCategory(
    @Id
    var id: Long?,
    var title: String,
    // unique column
    var publicId: String,
    var parentPublicId: String,

) : BaseEntity()
{
    fun isTop(): Boolean = parentPublicId == publicId

}
