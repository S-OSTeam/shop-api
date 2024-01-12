package sosteam.deamhome.domain.review.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.repository.custom.ReviewRepositoryCustom

@GraphQlRepository
interface ReviewRepository : ReactiveMongoRepository<Review, String>, ReviewRepositoryCustom {
}