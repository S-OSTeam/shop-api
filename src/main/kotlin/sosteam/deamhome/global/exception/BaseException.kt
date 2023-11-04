package sosteam.deamhome.global.exception

import org.springframework.http.HttpStatus

open class BaseException(
    var errorCode:ErrorCode = ErrorCode.BAD_REQUEST,
    override var message:String = errorCode.message,
    val httpStatus:HttpStatus = HttpStatus.BAD_REQUEST,

):Exception(message)