package sosteam.deamhome.domain.store.handler.request

import sosteam.deamhome.domain.store.entity.Store
import sosteam.deamhome.global.entity.DTO

data class StoreRequest (
    val storeName: String,
    val description: String?,
    val address: String,
    val businessLicenseNumber: String,
    val image: String?,
):DTO{
    override fun asDomain(): Store {
        return Store(
            id = null,
            storeName = storeName,
            description = description,
            address = address,
            businessLicenseNumber = businessLicenseNumber,
            image = image,
        )
    }
}