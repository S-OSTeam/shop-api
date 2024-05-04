package sosteam.deamhome.domain.store.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("store")
data class Store (
    @Id
    var id: Long?,

    @Column("store_name")
    val storeName: String,
    val description: String?,
    val address: String,

    @Column("business_license_number")
    val businessLicenseNumber: String,
    val image: String?,
) : BaseEntity(){
    var items: List<String> = listOf()
}