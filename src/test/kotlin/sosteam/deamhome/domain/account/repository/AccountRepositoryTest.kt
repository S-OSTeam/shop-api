package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.RepositoryBaseTest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime


class AccountRepositoryTest @Autowired constructor(
	private val accountRepository: AccountRepository,
) : RepositoryBaseTest() {

}
