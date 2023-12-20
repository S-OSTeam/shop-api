package sosteam.deamhome.domain.qna.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class FaqCategory(
    var title: String,

    ) : LogEntity() {
        

}