package sosteam.deamhome.domain.review.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import sosteam.deamhome.domain.review.entity.Review

interface ReviewRepository : ReactiveCrudRepository<Review, String>