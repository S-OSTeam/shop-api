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
	account: Account,
	item: Item,
	images: List<String>
) : LogEntity()