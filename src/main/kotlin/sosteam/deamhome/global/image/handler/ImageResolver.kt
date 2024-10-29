package sosteam.deamhome.global.image.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.global.image.handler.request.ImageRequest
import sosteam.deamhome.global.image.provider.ImageProvider

@RestController
class ImageResolver(private val imageProvider: ImageProvider) {
	
	@MutationMapping
	suspend fun uploadImage(@Argument request: ImageRequest): String {
		val ret = imageProvider.saveImage(
			request.image,
			request.outer,
			request.inner,
			request.resizeWidth,
			request.resizeHeight
		)
		
		return ret.fileUrl
	}
	
	@MutationMapping
	suspend fun uploadImageList(@Argument listRequest: List<ImageRequest>): List<String> {
		val ret = listRequest.map { request ->
			imageProvider.saveImage(
				request.image,
				request.outer,
				request.inner,
				request.resizeHeight,
				request.resizeWidth
			).fileUrl
		}
		
		return ret
	}
	
	@MutationMapping
	suspend fun deleteImage(@Argument path: String): Boolean {
		val ret = imageProvider.deleteImageByPath(path)
		
		return ret
	}
	
	@MutationMapping
	suspend fun deleteImageList(@Argument pathList: List<String>): List<Boolean> {
		val ret = pathList.map { path -> imageProvider.deleteImageByPath(path) }
		
		return ret
	}
}