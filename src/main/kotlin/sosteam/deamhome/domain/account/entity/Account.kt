package sosteam.deamhome.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.domain.question.entity.Question
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Table("account")
data class Account(
	@Id
	var id: Long?,
	// unique column
	@Column("user_id")
	val userId: String,
	var pwd: String,
	val sex: Boolean,
	var birthday: LocalDateTime,
	var zipcode: String,
	var address1: String,
	var address2: String?,
	var address3: String?,
	var address4: String?,
	
	// unique column
	var email: String,
	@Column("receive_mail")
	var receiveMail: Boolean,
	
	@Column("created_ip")
	val createdIp: String,
	@Column("admin_txt")
	var adminTxt: String = "",
	@Column("sns_id")
	val snsId: String? = null,
	val sns: SNS = SNS.NORMAL,
	
	// unique column
	var phone: String?,
	
	// unique column
	@Column("user_name")
	var userName: String,
	
	var point: Int = 0,
	
	var role: Role = Role.ROLE_GUEST,
	
	@Column("login_at")
	var loginAt: LocalDateTime,
	
	) : LogEntity() {
	companion object {
		const val maxWishListSize = 100
	}
	
	private var questions: ArrayList<String> = ArrayList()
	
	private var wishlist: ArrayList<String> = ArrayList()
	
	private var reviews: ArrayList<Long?> = ArrayList()
	
	@Column("review_report_ban_logs")
	private var reviewReportBanLogs: ArrayList<LocalDateTime> = ArrayList()
	
	@Column("review_report_logs")
	private var reviewReportLogs: ArrayList<LocalDateTime> = ArrayList()
	
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
	
	fun addQuestion(question: Question): List<String> {
//		questions.add(question.id)
		return questions
	}
	
	fun addReview(review: Long?): List<Long?> {
		reviews.add(review)
		return reviews
	}
	
	fun removeReview(review: Long?): List<Long?> {
		reviews.remove(review)
		return reviews
	}
	
	fun addReviewReportLog(report: LocalDateTime): List<LocalDateTime> {
		reviewReportLogs.add(report)
		return reviewReportLogs
	}
	
	fun getReviewReportLogs(): List<LocalDateTime> {
		return reviewReportLogs
	}
	
	fun addReviewReportBanLog(report: LocalDateTime): List<LocalDateTime> {
		reviewReportBanLogs.add(report)
		return reviewReportBanLogs
	}
	
	fun getReviewReportBanLogs(): List<LocalDateTime> {
		return reviewReportBanLogs
	}
	
	@JsonIgnore
	fun getAuthorities(): List<SimpleGrantedAuthority> {
		val simpleGrantedAuthorities = ArrayList<SimpleGrantedAuthority>()
		simpleGrantedAuthorities.add(SimpleGrantedAuthority(role.name))
		return simpleGrantedAuthorities
	}
}