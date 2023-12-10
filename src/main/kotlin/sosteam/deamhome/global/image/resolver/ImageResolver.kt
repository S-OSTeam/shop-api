package sosteam.deamhome.global.image.resolver

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.global.image.provider.ImageProvider

@RestController
class ImageResolver(private val imageProvider: ImageProvider) {
	
	@MutationMapping
	suspend fun uploadImage(@Argument image: FilePart): String {
		val ret = imageProvider.saveImage(image, "item", "review").awaitSingle()
		
		return ret.fileUrl
	}
	
	@MutationMapping
	suspend fun test(): String {
		return "test"
	}
	
	@MutationMapping
	suspend fun deleteImage(@Argument path: String): String {
		imageProvider.deleteImage(path)
		
		return "abc"
	}
}