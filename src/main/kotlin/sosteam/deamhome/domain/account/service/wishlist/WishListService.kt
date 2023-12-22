package sosteam.deamhome.domain.account.service.wishlist

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.WishListOverflowException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.item.repository.ItemRepository

@Service
class WishListService(
    private val accountRepository: AccountRepository,
    private val itemRepository: ItemRepository,
) {

    suspend fun getAllWishList(account:Account){
        //Todo: wishList에서 itemId 찾아서 리스트로 가져오기
        //Todo: 페이지네이션까지 적용
    }

    suspend fun addOrRemoveWishListItem(account: Account, itemId: String){
        if(account.isItemIdInWishlist(itemId)){ //이미 존재함
            deleteItem(account, itemId);
        }else{
            addItem(account, itemId);
        }
    }

    suspend fun addItem(account: Account, itemId: String ):Account{
        val maxWishListSize = 100

        if(account.getWishListSize()> maxWishListSize){
            throw WishListOverflowException()
        }else{
            account.addWishListItem(itemId)
            accountRepository.save(account).awaitSingle()
        }
        return account;
    }

    suspend fun deleteItem(account: Account, itemId:String):Account{
        account.removeWishListItem(itemId);
        return accountRepository.save(account).awaitSingle()
    }

}