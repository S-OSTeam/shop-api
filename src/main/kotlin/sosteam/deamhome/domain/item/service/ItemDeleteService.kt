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
	suspend fun deleteItemByPublicId(publicId: String): Long {
		// 존재하는 아이템인지 확인
		val item = itemRepository.findByPublicId(publicId)
			?: throw ItemNotFoundException()
		// 아이템의 이미지들 삭제
		for (imageUrl in item.imageUrls) {
			imageProvider.deleteImageByUrl(imageUrl)
		}
		//아이템 삭제
		return itemRepository.deleteByPublicId(publicId)
	}
}