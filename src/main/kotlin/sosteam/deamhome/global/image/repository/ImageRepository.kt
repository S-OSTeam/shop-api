package sosteam.deamhome.global.image.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.global.image.entity.Image

@GraphQlRepository
interface ImageRepository : CoroutineCrudRepository<Image, Long> {
	
	suspend fun findByPath(publicId: String): Image?
	
	suspend fun findByFileUrl(fileUrl: String): Image?
	
	suspend fun deleteByPath(path: String): Long
	
	suspend fun deleteByFileUrl(fileUrl: String): Long
	
}