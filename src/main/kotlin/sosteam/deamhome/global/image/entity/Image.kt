package sosteam.deamhome.global.image.entity

import lombok.Builder

@Builder
class Image(
	val fileName: String,
	val fileOriName: String,
	val path: String,
	val size: Long,
	val type: String,
	val fileUrl: String
)