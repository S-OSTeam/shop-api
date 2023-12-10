package sosteam.deamhome.global.image.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class NotImageException(
	errorCode: String = "THIE_IS_NOT_IMAGE",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "이미지 확장자가 아닙니다.(jpg, jpeg, png, gif, bmp, webp)"
) :
	CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
	override fun getMessage(): String = super.message
}