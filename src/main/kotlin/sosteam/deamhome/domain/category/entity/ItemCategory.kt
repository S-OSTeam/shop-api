package sosteam.deamhome.domain.category.entity

import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.BaseEntity

@Document
data class ItemCategory(
    var title: String,
    var sequence: Long = 0L,
    var parentSeq: Long? = null,
    var childrenSeq: MutableList<Long> = mutableListOf()
) : BaseEntity() {

    companion object {
        const val SEQUENCE_NAME = "CATEGORY_SEQUENCE"
        const val MAX_DEPTH = 2
    }
}
