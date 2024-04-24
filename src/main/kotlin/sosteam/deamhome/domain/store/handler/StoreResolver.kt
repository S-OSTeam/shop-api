package sosteam.deamhome.domain.store.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.store.handler.request.StoreRequest
import sosteam.deamhome.domain.store.handler.response.StoreResponse
import sosteam.deamhome.domain.store.service.StoreCreateService

@RestController
class StoreResolver (
    private val storeCreateService: StoreCreateService,
){
    // Todo: admin 계정에서만 생성 가능하게 변경..
    @MutationMapping
    suspend fun createStore(@Argument request: StoreRequest): StoreResponse{
        return storeCreateService.createStore(request)
    }

}