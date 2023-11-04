package sosteam.deamhome.global.exception

import org.springframework.http.HttpStatus

class DefaultException:BaseException{

    constructor(errorCode: ErrorCode) :super(errorCode = errorCode)

    constructor(errorCode: ErrorCode, httpStatus: HttpStatus): super(errorCode = errorCode, httpStatus = httpStatus)
}