package sosteam.deamhome.domain.item.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
@Transactional
class ItemDeleteService(
    private val itemRepository: ItemRepository,
    private val imageProvider: ImageProvider
) {
    suspend fun deleteItemByPublicId(publicId: Long): String {
        val item = itemRepository.findByPublicId(publicId)
            ?: throw ItemNotFoundException()

        for (image in item.images) {
            imageProvider.deleteImage(image.path)
        }

        itemRepository.deleteByPublicId(publicId)
        return item.title
    }
}