package sosteam.deamhome.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.domain.faq.entity.Faq
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
	companion object {
		const val maxWishListSize = 100
	}
	
	private var faqs: ArrayList<String> = ArrayList()
	
	private var wishlist: ArrayList<String> = ArrayList()
	
	private var reviews: ArrayList<String> = ArrayList()
	
	fun getWishList(): List<String> {
		return wishlist.toList()
	}
	
	fun addWishListItem(itemId: String): List<String> {
		wishlist.add(itemId)
		return wishlist
	}
	
	fun removeWishListItem(itemId: String): List<String> {
		wishlist.remove(itemId)
		return wishlist
	}
	
	fun isItemIdInWishlist(itemId: String): Boolean {
		return wishlist.contains(itemId)
	}
	
	fun getWishListSize(): Int {
		return wishlist.size
	}
	
	fun addFaq(faq: Faq): List<String> {
		faqs.add(faq.id)
		return faqs
	}
	
	fun addReview(review: String): List<String> {
		reviews.add(review)
		return reviews
	}
	
	@JsonIgnore
	fun getAuthorities(): List<SimpleGrantedAuthority> {
		val simpleGrantedAuthorities = ArrayList<SimpleGrantedAuthority>()
		simpleGrantedAuthorities.add(SimpleGrantedAuthority(role.name))
		return simpleGrantedAuthorities
	}
}