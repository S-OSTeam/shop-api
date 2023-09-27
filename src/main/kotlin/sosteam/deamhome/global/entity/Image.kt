package sosteam.deamhome.global.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Builder
class Image(
	val fileName: String,
	val fileOriginName: String,
	val size: Int,
	val type: String,
	val fileUrl: String
) : BaseEntity()