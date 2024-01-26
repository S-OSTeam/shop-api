package sosteam.deamhome.global.category.handler.response

import sosteam.deamhome.global.category.entity.CategoryEntity

abstract class CategoryTreeResponse<T : CategoryEntity> {
    abstract val publicId: String
    abstract val title: String
    abstract val children: MutableList<CategoryTreeResponse<T>>

}