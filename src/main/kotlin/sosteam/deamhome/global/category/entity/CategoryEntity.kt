package sosteam.deamhome.global.category.entity

import org.springframework.data.annotation.Transient
import sosteam.deamhome.global.entity.BaseEntity
import java.util.*

abstract class CategoryEntity(@Transient var maxDepth: Int): BaseEntity() {
    abstract var publicId: UUID
    abstract var parentPublicId: UUID
    abstract var title: String

    fun isTop(): Boolean = parentPublicId == publicId
}