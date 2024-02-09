package sosteam.deamhome.domain.account.entity

data class CartItem(val itemId: String, val userId: String,  var cnt: Int, var check: Boolean)
