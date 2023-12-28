package sosteam.deamhome.domain.category.entity

import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity

@Document
data class ItemCategory(
    var title: String,
    var publicId: Long = 0L,
    var parentPublicId: Long? = null
) : BaseEntity()
