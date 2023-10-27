package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.dto.request.AccountRequestDTO
import sosteam.deamhome.domain.account.dto.response.AccountResponseDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@Service
class AccountDataControlService(
        private val accountStatusRepository: AccountStatusRepository,
        private val accountRepository: AccountRepository,
){

    suspend fun deleteAccount(account: Account){
        accountRepository.delete(account).awaitSingle()
    }

    suspend fun createAccount(accountRequestDTO: AccountRequestDTO):Mono<AccountResponseDTO>{

        if(accountRepository.findByUserId(accountRequestDTO.userId)!=null){
            //TODO : 에러 처리

        }
        val accountStatus = AccountStatus(
            userId = accountRequestDTO.userId,
            snsId = accountRequestDTO.snsId,
            status = Status.LIVE,
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
            LocalDateTime.now()
        )
        accountStatusRepository.insert(accountStatus).awaitSingle()
        val result = accountRepository.insert(account).awaitSingle()

        return Mono.just(result)
            .map{account -> AccountResponseDTO.fromAccount(account)}
    }



}