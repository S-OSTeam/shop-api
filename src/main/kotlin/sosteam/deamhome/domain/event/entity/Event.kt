package sosteam.deamhome.domain.event.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDateTime

@Table("event")
data class Event(
    @Id
    var id: Long?,
    @Column("started_at")
    var startedAt: LocalDateTime,
    @Column("ended_at")
    var endedAt: LocalDateTime,
    var title: String,
    var contents: String?,
    var thumbnail: String?,
    var items: MutableList<String> = mutableListOf(),

): BaseEntity(){
    var images: MutableList<String> = mutableListOf()
}