package sosteam.deamhome.domain.faq.entity

import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("faq_category")
class FaqCategory(
	var title: String,
	
	) : LogEntity() {
	
//	@DocumentReference(lazy = true)
	val faqs: ArrayList<Faq> = ArrayList()
	
	fun addFaq(faq: Faq): List<Faq> {
		faqs.add(faq)
		return faqs
	}
}