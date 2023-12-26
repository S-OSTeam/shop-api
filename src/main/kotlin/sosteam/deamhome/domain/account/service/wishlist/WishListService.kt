package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.WishListOverflowException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.category.exception.CategoryNotFoundException
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.domain.item.entity.dto.response.ItemResponseDTO
import sosteam.deamhome.domain.item.exception.ItemNotFoundException
import sosteam.deamhome.domain.item.repository.ItemRepository
import java.awt.print.Pageable
import java.util.concurrent.Flow

@Service
class WishListService(
    private val accountRepository: AccountRepository,
    private val itemRepository: ItemRepository,
    private val accountValidService: AccountValidService,
) {

    suspend fun getAllWishList(userId: String, page:Int):List<ItemResponseDTO>{
        //Todo: wishList에서 itemId 찾아서 리스트로 가져오기
        val pageSize = 10
        val account = accountValidService.getAccountByUserId(userId)
        val pageRequest:PageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")))
        return itemRepository.findByIdIn(account.getWishlist(),pageRequest)
            .toList()
            .map { ItemResponseDTO.fromItem(it) }

    }

    suspend fun addOrRemoveWishListItem(userId: String, itemId: String):List<String>{
        println("addOrRemoveWishListItem")
        val account = accountValidService.getAccountByUserId(userId)
        println("add or delete")
        if(account.isItemIdInWishlist(itemId)){ //이미 존재함
            deleteItem(account, itemId);
        }else{
            addItem(account, itemId);
        }
        return account.getWishlist()
    }

    suspend fun addItem(account: Account, itemId: String ):Account{
        val maxWishListSize = 100
        println("add")
        if(account.getWishListSize()> maxWishListSize){
            throw WishListOverflowException()
        }else{
            account.addWishListItem(itemId)
            accountRepository.save(account).awaitSingle()

            val item = itemRepository.findItemById(itemId)?.let{
                it.wishCnt++
                itemRepository.save(it).awaitSingle()
            }?: throw ItemNotFoundException()

        }
        return account;
    }

    suspend fun deleteItem(account: Account, itemId:String):Account{
        println("delete")
        account.removeWishListItem(itemId);
        val item = itemRepository.findItemById(itemId)?.let{
            it.wishCnt--
            itemRepository.save(it).awaitSingle()
        }?: throw ItemNotFoundException()
        return accountRepository.save(account).awaitSingle()
    }

}