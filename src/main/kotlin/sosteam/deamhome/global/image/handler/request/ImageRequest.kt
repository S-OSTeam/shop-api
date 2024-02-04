package sosteam.deamhome.global.image.handler.request

import org.springframework.http.codec.multipart.FilePart

data class ImageRequest(
	val image: FilePart,
	val outer: String,
	val inner: String,
	val resizeWidth: Int,
	val resizeHeight: Int
)