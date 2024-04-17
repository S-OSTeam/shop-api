package sosteam.deamhome.domain.review.entity

enum class ReviewFavor(val value: Int) {
	LIKE(1), DISLIKE(-1), NONE(0)
}