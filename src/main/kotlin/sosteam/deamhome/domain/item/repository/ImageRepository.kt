package sosteam.deamhome.domain.item.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import sosteam.deamhome.global.entity.Image

@Repository
interface ImageRepository: ReactiveMongoRepository<Image, String> {
}