package sosteam.deamhome.domain.category.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.category.dto.response.ItemDetailCategoryResponse
import sosteam.deamhome.domain.category.service.DetailCategorySearchService

@RestController
class DetailCategoryQuery (
    private val detailCategorySearchService: DetailCategorySearchService
){
    @QueryMapping
    suspend fun getItemDetailCategoryByTitle(@Argument title: String): ItemDetailCategoryResponse {
        //없으면 어떡하지? 에러처리인가? 지금은 없으면 비어잇음 그냥
        //근데 response 에다가 id 넣어도 되나? 뺄까 말까. 빼면 변수가 title 밖에 없음
        return detailCategorySearchService.getItemDetailCategoryByTitle(title)
    }
}