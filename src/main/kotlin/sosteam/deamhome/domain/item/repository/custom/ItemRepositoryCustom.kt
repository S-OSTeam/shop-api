package sosteam.deamhome.domain.item.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.handler.request.ItemSearchRequest

interface ItemRepositoryCustom {
	fun searchItem(request: ItemSearchRequest): Flow<Item>
}