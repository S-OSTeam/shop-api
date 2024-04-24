package sosteam.deamhome.domain.store.entity

import org.springframework.data.annotation.Id
import sosteam.deamhome.global.entity.BaseEntity

data class Store (
    @Id
    var id: Long?,
    val storeName: String,
    val description: String?,
    val address: String,
    val businessLicenseNumber: String,
    val image: String?,
) : BaseEntity(){
    var items: List<String> = listOf()
}