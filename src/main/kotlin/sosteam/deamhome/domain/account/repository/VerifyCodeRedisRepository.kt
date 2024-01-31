package sosteam.deamhome.domain.account.repository

import lombok.RequiredArgsConstructor
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.AccountVerifyCode
import java.time.Duration

@Repository
@RequiredArgsConstructor
interface VerifyCodeRedisRepository: CrudRepository<AccountVerifyCode, String> {
}