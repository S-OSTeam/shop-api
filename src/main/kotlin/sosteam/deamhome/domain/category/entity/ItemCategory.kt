package sosteam.deamhome.domain.category.entity

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.category.entity.CategoryEntity

@Document
data class ItemCategory(
    override var title: String,
    @Indexed(unique = true)
    override var publicId: String,
    override var parentPublicId: String
) : CategoryEntity(2) {
}
