package sosteam.deamhome.domain.category.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDateTime

@Table(value = "item_category")
data class ItemCategory(
    @Id
    var id: Long? = null,
    var title: String,
    var publicId: String,
    var parentPublicId: String,

) : BaseEntity()
{
    fun isTop(): Boolean = parentPublicId == publicId

}
