package sosteam.deamhome.domain.store.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class StoreNameAlreadyExistException (
    errorCode: String = "STORE_NAME_ALREADY_EXIST",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "이미 존재하는 스토어 이름입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}