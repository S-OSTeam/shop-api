package sosteam.deamhome.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Setter
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.domain.faq.entity.Faq
import sosteam.deamhome.domain.item.entity.Wishlist
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime


@Document(collection = "accounts")
data class Account(
	
	@Indexed(unique = true)
	val userId: String,
	var pwd: String,
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
	
	var loginAt: LocalDateTime,
	
	) : LogEntity() {
	
	private val faqs: ArrayList<String> = ArrayList()
	
	@Setter
	private val wishlist: ArrayList<Wishlist> = ArrayList()
	
	private val reviews: ArrayList<String> = ArrayList()
	
	fun addFaq(faq: Faq): List<String> {
		faqs.add(faq.id)
		return faqs
	}
	
	fun addReview(review: String): List<String> {
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