package sosteam.deamhome.domain.account.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import sosteam.deamhome.domain.account.entity.AccountVerifyCode

@Repository
interface VerifyCodeRedisRepository: CrudRepository<AccountVerifyCode, String> {
}