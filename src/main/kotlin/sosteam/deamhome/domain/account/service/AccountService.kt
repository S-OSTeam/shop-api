package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.dto.AccountRequestDTO
import sosteam.deamhome.domain.account.dto.AccountResponseDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@Service
class AccountService(
    private val accountRepository: AccountRepository
){
    fun getAllAccounts():Flux<Account>{
        return accountRepository.findAll()
    }

    fun createAccount(accountRequestDTO:AccountRequestDTO):Mono<AccountResponseDTO>{
        val accountStatus = AccountStatus(
            userId = accountRequestDTO.userId,
            snsId = accountRequestDTO.snsId,
            status = Status.LIVE,
            account = null, //일단 null
        )
        val account = Account(
            accountRequestDTO.userId,
            accountRequestDTO.pwd,
            accountRequestDTO.sex,
            accountRequestDTO.birtyday,
            accountRequestDTO.zipcode,
            accountRequestDTO.address1,
            accountRequestDTO.address2,
            accountRequestDTO.address3,
            accountRequestDTO.address4,
            accountRequestDTO.email,
            accountRequestDTO.receiveMail,
            accountRequestDTO.createdIp,
            "",
            accountRequestDTO.snsId,
            accountRequestDTO.sns,
            accountRequestDTO.phone,
            accountRequestDTO.userName,
            accountRequestDTO.point,
            Role.ROLE_GUEST,
            accountStatus,
            LocalDateTime.now()
        )
        return accountRepository.save(account)
            .map{account -> AccountResponseDTO.fromAccount(account)}
    }

    fun updateAccountStatus(account: Account): Mono<Account>{
        return accountRepository.save(account)
    }

}