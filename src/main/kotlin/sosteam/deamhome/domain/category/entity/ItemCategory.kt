package sosteam.deamhome.domain.category.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.category.entity.CategoryEntity
import java.util.UUID

@Table("item_category")
data class ItemCategory(
    @Id
    var id: Long?,
    override var title: String,
) : CategoryEntity(2){
    override var publicId: UUID = UUID.randomUUID()
    override var parentPublicId: UUID = UUID.randomUUID()
}
