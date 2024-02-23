package sosteam.deamhome.domain.account.service

import org.springframework.beans.factory.annotation.Autowired
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.test.RepositoryBaseTest


class AccountRepositoryTest @Autowired constructor(
	private val accountRepository: AccountRepository,
) : RepositoryBaseTest()
