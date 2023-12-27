package sosteam.deamhome.domain.category.entity

import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity

@Document
data class ItemCategory(
    var title: String,
    var publicId: Long = 0L,
    var parentPublicId: Long? = null
) : BaseEntity() {

    companion object {
        const val SEQUENCE_NAME = "ITEM_CATEGORY_SEQUENCE"
        const val MAX_DEPTH = 2
    }
}
