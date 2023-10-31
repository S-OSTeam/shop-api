package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Status

@Service
class AccountStatusService(
    private val accountStatusRepository: AccountStatusRepository,
    private val accountRepository: AccountRepository,
    @Autowired
    private val reactiveMongoOperations: ReactiveMongoOperations,
){

    suspend fun updateAccountStatus(
        userId : String,
        status : Status,
    ): Mono<AccountStatus> {
        val accountStatus: AccountStatus? = accountStatusRepository.findByUserId(userId)
        if(accountStatus == null){
            //에러처리
        }
        accountStatus!!.status = status
        accountStatusRepository.save(accountStatus).awaitSingle() // accountStatus 상태 바꿔줌

        if(status == Status.DORMANT){ // 휴면계정으로의 전환
            val account: Account? = accountRepository.findByUserId(userId)
            if(account == null){
                //TODO : 에러처리
            }
            reactiveMongoOperations.save(account!!, "accounts_dormant").awaitSingleOrNull()
            accountRepository.delete(account).awaitSingle()

        }else if(status == Status.LIVE){ // 계정 활성화
            val query = Query().addCriteria(Criteria.where("userId").`is`(userId))
            val account: Account? = reactiveMongoOperations
                .findOne(query, Account::class.java, "accounts_dormant")
                .awaitSingleOrNull()
            if(account == null){
                //TODO : 에러처리
            }
            reactiveMongoOperations
                .remove(query, Account::class.java, "accounts_dormant")
                .awaitSingleOrNull()
            accountRepository.save(account!!).awaitSingle()

        }


        return Mono.just(accountStatus)
    }
}