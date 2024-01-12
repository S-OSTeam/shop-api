
package sosteam.deamhome.global.sequence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class DatabaseSequence (
    @Id
    var id: String? = null,
    var sequence: Long = 0
)