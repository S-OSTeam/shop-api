package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository

@Service
class CategoryDeleteService ( private val itemCategoryRepository: ItemCategoryRepository ) {

    //리턴값 없어도 되나? Graphql 에서는 리턴값 없는건 안돼서 Boolean 넣어놨는데 null 만 나옴
    suspend fun deleteItemCategoryById(id: String){
        itemCategoryRepository.deleteItemCategoryById(id)
    }

}