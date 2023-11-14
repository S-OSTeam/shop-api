package sosteam.deamhome.global.image.provider

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import sosteam.deamhome.global.image.entity.Image
import sosteam.deamhome.global.image.exception.NotImageException
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Component
class ImageProvider(
	@Value("\${deamhome.image.dir}")
	private val imageDir: String,
	@Value("\${deamhome.url}")
	private val imageUrl: String,
) {
	suspend fun saveImage(image: FilePart, outer: String, inner: String): Mono<Image> {
		var regex = "(.*?).(jpg|jpeg|png|gif|bmp|webp)$".toRegex()
		
		if (!regex.matches(image.filename()))
			throw NotImageException()
		
		val (name, type) = image.filename().split('.')
		
		val imageUuid = UlidCreator.getMonotonicUlid().toString().replace("-", "") + "." + type
		var filePath = "${imageDir}/${outer}/"
		var url = "${imageUrl}/${outer}/"
		
		//inner 폴더가 필요한 경우 추가
		if (inner.isNotBlank()) {
			filePath += "${inner}/"
			url += "${inner}/"
		}
		url += "${imageUuid}"
		File(filePath).mkdirs()
		
		val file = File("x:/images/item/review/", imageUuid)
		
		val ret = Image(
			imageUuid,
			image.filename(),
			filePath + imageUuid,
			file.length(),
			type,
			url
		)
		
		
		return image.transferTo(file).then(
			Mono.just(ret)
		)
	}
	
	suspend fun deleteImage(filePath: String): Boolean {
		val path = Paths.get(filePath)
		return Files.deleteIfExists(path)
	}
}