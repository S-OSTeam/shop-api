package sosteam.deamhome.domain.faq.entity

import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.LogEntity

@Table("faq")
class Faq(
	var title: String,
	var content: String,
	var answer: String,
	user: Account,
	category: FaqCategory
) : LogEntity() {
	
	
//	@DocumentReference(lazy = true)
	val user: Account = user
	
//	@DocumentReference(lazy = true)
	lateinit var admin: Account
	
//	@DocumentReference(lazy = true)
	var category: FaqCategory = category
}