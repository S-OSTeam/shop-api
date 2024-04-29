package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.log.entity.CartLog

@GraphQlRepository
interface CartLogRepository: CoroutineCrudRepository<CartLog, Long> {
}