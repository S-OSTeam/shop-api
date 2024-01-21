package sosteam.deamhome.domain.review.entity

import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.entity.LogEntity

class Review(
	var title: String,
	var content: String,
	var like: Int = 0,
	var score: Double = 0.0,
	var status: Boolean = false,
	var account: Account,
	var item: Item,
	var images: List<String>,
	var likeUsers: List<String>,
) : LogEntity()