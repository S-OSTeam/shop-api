package sosteam.deamhome.domain.review.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.domain.review.repository.custom.ReviewRepositoryCustom

@GraphQlRepository
interface ReviewRepository : ReactiveCrudRepository<Review, String>, ReviewRepositoryCustom