package sosteam.deamhome.global.category.handler.response

import sosteam.deamhome.global.category.entity.CategoryEntity
import java.util.UUID

abstract class CategoryTreeResponse<T : CategoryEntity> {
    abstract val publicId: UUID
    abstract val title: String
    abstract val children: MutableList<CategoryTreeResponse<T>>

}