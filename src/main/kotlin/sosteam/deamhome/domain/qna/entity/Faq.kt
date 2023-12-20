package sosteam.deamhome.domain.qna.entity

import lombok.Builder
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.LogEntity

@Document
@Builder
class Faq(
    var title: String,
    var content: String,
    var answer: String,
    faqCategory: FaqCategory
) : LogEntity() {

    var categoryId: String = faqCategory.id
}