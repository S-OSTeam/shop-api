package sosteam.deamhome.domain.item.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.entity.Item

interface ItemRepositoryCustom {
    fun findItemsContainTitle(title: String): Flow<Item>

}