package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponseDTO
import sosteam.deamhome.domain.category.service.DetailCategorySearchService

@RestController
class DetailCategoryQuery (
    private val detailCategorySearchService: DetailCategorySearchService
){
    @QueryMapping
    suspend fun getItemDetailCategoryByTitle(@Argument title: String): ItemDetailCategoryResponseDTO {
        //근데 response 에다가 id 넣어도 되나? 뺄까 말까. 빼면 변수가 title 밖에 없음
        return detailCategorySearchService.getItemDetailCategoryByTitle(title)
    }
}