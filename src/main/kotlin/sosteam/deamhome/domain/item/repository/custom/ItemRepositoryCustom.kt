package sosteam.deamhome.domain.item.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.entity.Item

interface ItemRepositoryCustom {
    fun getItemsContainsTitle(title: String): Flow<Item>

    fun getItemsByOption(itemIdList: List<String>?, title: String?): Flow<Item>
}