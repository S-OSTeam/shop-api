package sosteam.deamhome.domain.account.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.AccountVerifyCode
import sosteam.deamhome.global.attribute.VerifyType

/*@GraphQlRepository
interface AccountVerifyCodeRepository: ReactiveMongoRepository<AccountVerifyCode, String>,
        ReactiveQuerydslPredicateExecutor<AccountVerifyCode> {

        suspend fun findAccountVerifyCodeByEmailAndType(email: String, type: VerifyType): AccountVerifyCode?

        suspend fun findAccountVerifyCodeByVerifyCode(verifyCode: String): AccountVerifyCode?

        suspend fun deleteAccountVerifyCodesByVerifyCode(verifyCode: String)
                
        suspend fun deleteAccountVerifyCodesByEmailAndType(email: String, type: VerifyType)
}*/