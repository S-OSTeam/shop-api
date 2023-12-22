package sosteam.deamhome.domain.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.category.repository.ItemCategoryRepository


@Service
@Transactional
class ItemCategoryUpdateService (
    private val itemCategoryRepository: ItemCategoryRepository
) {


}