package sosteam.deamhome.global.category.entity

import org.springframework.data.annotation.Transient
import sosteam.deamhome.global.entity.BaseEntity

abstract class Category(maxDepth: Int): BaseEntity() {
    abstract var publicId: String
    abstract var parentPublicId: String
    abstract var title: String
    @Transient
    var maxDepth: Int = maxDepth

    fun isTop(): Boolean = parentPublicId == publicId
}