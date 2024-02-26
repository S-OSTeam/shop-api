package sosteam.deamhome.domain.event.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDateTime

@Table("event")
data class Event(
    @Id
    var id: Long?,
    var started: LocalDateTime,
    var ended: LocalDateTime,
    var title: String,
    var contents: String?,
    var thumbnail: String?,
    var items: MutableList<String> = mutableListOf(),

): BaseEntity(){
    var images: MutableList<String> = mutableListOf()
}