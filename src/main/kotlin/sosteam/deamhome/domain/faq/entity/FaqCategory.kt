package sosteam.deamhome.domain.faq.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class FaqCategory(
	var title: String,
	
	) : LogEntity() {
	
	@DocumentReference(lazy = true)
	val faqs: ArrayList<Faq> = ArrayList()
	
	fun addFaq(faq: Faq): List<Faq> {
		faqs.add(faq)
		return faqs
	}
}