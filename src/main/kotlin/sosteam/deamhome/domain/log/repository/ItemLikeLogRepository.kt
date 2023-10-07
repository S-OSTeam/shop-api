package sosteam.deamhome.domain.log.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import sosteam.deamhome.domain.log.entity.ItemLikeLog

interface ItemLikeLogRepository: ReactiveMongoRepository<ItemLikeLog,String> {
}