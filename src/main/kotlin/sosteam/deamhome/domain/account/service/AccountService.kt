package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.dto.AccountRequestDTO
import sosteam.deamhome.domain.account.dto.AccountResponseDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime
import java.util.logging.ErrorManager

@Service
class AccountService(
        private val accountStatusRepository: AccountStatusRepository,
        private val accountRepository: AccountRepository,
        @Autowired
        private val reactiveMongoOperations: ReactiveMongoOperations,
){
    fun getAllAccounts():Flux<Account>{
        return accountRepository.findAll()
    }
    suspend fun deleteAccount(account: Account){
        accountRepository.delete(account).awaitSingle()
    }

    suspend fun updateAccountStatus(
            userId : String,
            status : Status,
    ):Mono<AccountStatus>{
        val accountStatus:AccountStatus? = accountStatusRepository.findByUserId(userId).awaitFirstOrDefault(null)
        if(accountStatus == null){
            //에러처리
        }
        accountStatus!!.status = status
        accountStatusRepository.save(accountStatus).awaitSingle() // accountStatus 상태 바꿔줌

        if(status == Status.DORMANT){ // 휴면계정으로의 전환
            val account:Account? = accountRepository.findByUserId(userId).awaitFirstOrDefault(null)
            if(account == null){
                //에러처리
            }
            reactiveMongoOperations.save(account!!, "accounts_dormant").awaitSingleOrNull()
            deleteAccount(account)
        }else if(status == Status.LIVE){ // 계정 활성화
            val query = Query().addCriteria(Criteria.where("userId").`is`(userId))
            val account:Account? = reactiveMongoOperations
                    .findOne(query, Account::class.java, "accounts_dormant")
                    .awaitSingleOrNull()
            if(account == null){
                //에러처리
            }
            reactiveMongoOperations
                    .remove(query, Account::class.java, "accounts_dormant")
                    .awaitSingleOrNull()
            accountRepository.save(account!!).awaitSingle()

        }


        return Mono.just(accountStatus)
    }

    suspend fun createAccount(accountRequestDTO:AccountRequestDTO):Mono<AccountResponseDTO>{

        if(accountRepository.findByUserId(accountRequestDTO.userId)!=null){
            //에러 처리

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