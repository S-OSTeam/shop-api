package sosteam.deamhome.domain.item.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.entity.dto.ItemDTO

interface ItemRepositoryCustom {
    fun getItemsContainsTitle(title: String): Flow<ItemDTO>

    fun getItemsByOption(itemIdList: List<String>?, title: String?): Flow<ItemDTO>
}