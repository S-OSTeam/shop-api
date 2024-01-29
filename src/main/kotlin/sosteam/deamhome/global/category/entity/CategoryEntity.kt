package sosteam.deamhome.global.category.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.Transient
import sosteam.deamhome.global.entity.BaseEntity

abstract class CategoryEntity(@Transient var maxDepth: Int): BaseEntity() {
    open var publicId: String = UlidCreator.getMonotonicUlid().toString().replace("-", "")
    abstract var parentPublicId: String
    abstract var title: String

    fun isTop(): Boolean = parentPublicId == publicId
}