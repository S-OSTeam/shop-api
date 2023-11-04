package sosteam.deamhome.global.exception

private const val ACCOUNT_NUMBERING = 1000
private const val AUTH_NUMBERING = 2000
private const val CART_NUMBERING = 3000
private const val CATEGORY_NUMBERING = 4000
private const val FAQ_NUMBERING = 5000
private const val ITEM_NUMBERING = 6000
private const val LOG_NUMBERING = 7000
private const val ORDER_NUMBERING = 8000
private const val POPUP_NUMBERING = 9000
private const val REVIEW_NUMBERING = 10000
private const val SERVER_NUMBERING = 11000


enum class ErrorCode(val code:Int, val message:String){

    ACCOUNT_NOT_FOUND(ACCOUNT_NUMBERING+1,"어카운트를 찾을 수 없습니다"),
    DUPLICATE_USER_ID(ACCOUNT_NUMBERING + 2, "중복된 사용자 ID입니다"),
    ACCOUNT_STATUS_NOT_FOUND(ACCOUNT_NUMBERING + 3, "어카운트상태 데이터를 찾을 수 없습니다"),

    ITEM_NOT_FOUNT(ITEM_NUMBERING+1,"아이템을 찾을 수 없습니다."),

    BAD_REQUEST(SERVER_NUMBERING + 1, "잘못된 요청입니다."),
}