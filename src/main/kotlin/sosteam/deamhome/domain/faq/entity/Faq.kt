package sosteam.deamhome.domain.faq.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class Faq(
	var title: String,
	var content: String,
	var answer: String,
	user: Account,
	category: FaqCategory
) : LogEntity() {
	
	
	@DBRef(lazy = true)
	val user: Account = user
	
	@DBRef(lazy = true)
	lateinit var admin: Account
	
	@DBRef(lazy = true)
	var category: FaqCategory = category
}