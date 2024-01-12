package sosteam.deamhome.domain.category.entity

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity

@Document
data class ItemCategory(
    var title: String,
    @Indexed(unique = true)
    var publicId: Long,
    var parentPublicId: Long
) : BaseEntity() {
    fun isTop(): Boolean = parentPublicId == publicId

}
