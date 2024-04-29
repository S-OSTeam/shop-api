package sosteam.deamhome.global.attribute

enum class VerifyType(val subject: String, val body: String) {
    SIGNUP(
        "deamhome 회원가입 인증 코드",
        "회원가입을 위한 인증입니다."
    ),
    FIND_USERID(
        "deamhome 아이디 찾기 인증 코드",
        "아이디 찾기를 위한 인증입니다."
    ),
    CHANGE_PWD(
        "deamhome 비밀번호 변경 인증 코드",
        "비밀번호 변경을 위한 인증입니다."
    ),
    CHANGE_USER_INFO(
        "deamhome 회원 정보 변경 인증 코드",
        "회원 정보 변경을 위한 인증입니다."
    ),
    RESTORE_USER(
        "deamhome 계정 복구 인증 코드",
        "계정 복구를 위한 인증입니다."
    )
}

