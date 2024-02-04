package sosteam.deamhome.global.image.entity

import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity

@Table("image")
class Image(
	@Column("file_name")
	val fileName: String,
	@Column("file_ori_name")
	val fileOriName: String,
	val path: String,
	var size: Long,
	val type: String,
	@Column("file_url")
	val fileUrl: String,
	@Column("outer_path")
	val outerPath: String,
	@Column("inner_path")
	val innerPath: String?,
	var width: Int,
	var height: Int,
) : BaseEntity() {
	@Id
	@Setter
	var id: Long? = null
}