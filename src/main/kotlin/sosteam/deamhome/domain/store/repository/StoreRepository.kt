package sosteam.deamhome.domain.store.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.store.entity.Store

@GraphQlRepository
interface StoreRepository : CoroutineCrudRepository<Store, Long>{

}
