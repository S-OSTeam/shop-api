package sosteam.deamhome.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Setter
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.domain.faq.entity.Faq
import sosteam.deamhome.domain.item.entity.Wishlist
import sosteam.deamhome.domain.review.entity.Review
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime


@Document(collection = "accounts")
data class Account(
	
	@Indexed(unique = true)
	val userId: String,
	val pwd: String,
	val sex: Boolean,
	var birtyday: LocalDateTime,
	var zipcode: String,
	var address1: String,
	var address2: String?,
	var address3: String?,
	var address4: String?,
	
	@Indexed(unique = true)
	var email: String,
	var receiveMail: Boolean,
	
	val createdIp: String,
	var adminTxt: String = "",
	val snsId: String? = null,
	val sns: SNS = SNS.NORMAL,
	
	@Indexed(unique = true)
	var phone: String,
	
	@Indexed(unique = true)
	var userName: String,
	
	var point: Int = 0,
	
	var role: Role = Role.ROLE_GUEST,
	
	var lastLoginDateTime: LocalDateTime,
	
	) : LogEntity() {
	
	private val faqs: ArrayList<Faq> = ArrayList()
	
	@DocumentReference(lazy = true)
	@Setter
	private val wishlist: Wishlist? = null
	
	@DocumentReference(lazy = true)
	private val reviews: ArrayList<Review> = ArrayList()
	
	fun addFaq(faq: Faq): List<Faq> {
		faqs.add(faq)
		return faqs
	}
	
	fun addReview(review: Review): List<Review> {
		reviews.add(review)
		return reviews
	}
	
	@JsonIgnore
	fun getAuthorities(): Collection<GrantedAuthority?> {
		val simpleGrantedAuthorities = ArrayList<SimpleGrantedAuthority?>()
		simpleGrantedAuthorities.add(SimpleGrantedAuthority(role.name))
		return simpleGrantedAuthorities
	}
}