package sosteam.deamhome.global.exception

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.reactive.error.*
import sosteam.deamhome.global.BaseTest

class GlobalExceptionHandlerTest(
    @Autowired
    private val globalExceptionHandler:GlobalExceptionHandler
): BaseTest(){
    @Test
    @DisplayName("exception handler 테스트")
    fun testGlobalExceptionHandlerLogging() {

        assertThrows<DefaultException> {
            throw DefaultException(errorCode = ErrorCode.ACCOUNT_NOT_FOUND)
        }

    }
}