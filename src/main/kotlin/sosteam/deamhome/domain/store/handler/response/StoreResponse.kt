package sosteam.deamhome.domain.store.handler.response

import sosteam.deamhome.domain.store.entity.Store


data class StoreResponse (
    val id: Long?,
    val storeName: String,
    val description: String?,
    val address: String,
    val businessLicenseNumber: String,
    val image: String?,
    val items: List<String> = listOf()
){
    companion object{
        fun fromStore(store:Store): StoreResponse{
            return StoreResponse(
                id = store.id,
                storeName = store.storeName,
                description = store.description,
                address = store.address,
                businessLicenseNumber = store.businessLicenseNumber,
                image = store.image,
                items = store.items,
            )
        }
    }
}