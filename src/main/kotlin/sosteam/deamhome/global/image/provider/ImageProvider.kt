package sosteam.deamhome.global.image.provider

import com.github.f4b6a3.ulid.UlidCreator
import kotlinx.coroutines.reactor.awaitSingle
import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import sosteam.deamhome.global.image.entity.Image
import sosteam.deamhome.global.image.exception.NotImageException
import sosteam.deamhome.global.image.repository.ImageRepository
import sosteam.deamhome.global.provider.log
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

@Component
class ImageProvider(
	@Value("\${deamhome.image.dir}")
	private val imageDir: String,
	@Value("\${deamhome.url}")
	private val imageUrl: String,
	private val imageRepository: ImageRepository
) {
	
	suspend fun saveImage(
		image: FilePart,
		outer: String,
		inner: String,
		resizeWidth: Int,
		resizeHeight: Int
	): Image {
		val savedImage = saveImageFile(image, outer, inner, resizeWidth, resizeHeight).awaitSingle()
		resizeImage(savedImage)
		
		return savedImage
	}
	
	suspend fun saveImageFile(
		image: FilePart,
		outer: String,
		inner: String,
		resizeWidth: Int,
		resizeHeight: Int
	): Mono<Image> {
		var regex = "(.*?).(jpg|jpeg|png|gif|bmp|webp|PNG)$".toRegex()
		
		if (!regex.matches(image.filename()))
			throw NotImageException()
		
		val imageNameParse = image.filename().split('.')
		val name = imageNameParse.first()
		val type = imageNameParse.last()
		
		log().debug(name)
		val imageUuid = UlidCreator.getMonotonicUlid().toString().replace("-", "") + "." + type
		var filePath = "${imageDir}/${outer}/"
		var url = "${imageUrl}/${outer}/"
		
		log().debug(url)
		//inner 폴더가 필요한 경우 추가
		if (inner.isNotBlank()) {
			filePath += "${inner}/"
			url += "${inner}/"
		}
		url += "${imageUuid}"
		File(filePath).mkdirs()
		
		log().debug(filePath)
		val file = File(filePath, imageUuid)
		
		
		return image.transferTo(file)
			.thenReturn(
				Image(
					imageUuid,
					image.filename(),
					filePath + imageUuid,
					file.length(),
					type,
					url,
					outer,
					inner,
					resizeWidth,
					resizeHeight
				)
			)
	}
	
	suspend fun resizeImage(image: Image): Boolean {
		val file = File(image.path)
		
		log().debug(file.length().toString())
		image.size = file.length()
		
		val fis = FileInputStream(file)
		//image resize
		val bufferedImage: BufferedImage = ImageIO.read(fis)
		val resizedImage =
			Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, image.width, image.height)
		ImageIO.write(resizedImage, image.type, file)
		
		imageRepository.save(image)
		
		return true
	}
	
	@Transactional
	suspend fun deleteImageByPath(filePath: String): Boolean {
		val path = Paths.get(filePath)
		Files.deleteIfExists(path)
		
		return imageRepository.deleteByPath(filePath) > 0L
	}
	
	@Transactional
	suspend fun deleteImageByUrl(fileUrl: String): Boolean {
		val image = imageRepository.findByFileUrl(fileUrl)
		val path = Paths.get(image?.path)
		Files.deleteIfExists(path)
		
		return imageRepository.deleteByFileUrl(fileUrl) > 0L
	}
}