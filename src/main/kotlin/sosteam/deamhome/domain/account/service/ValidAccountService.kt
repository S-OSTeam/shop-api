package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class ValidAccountService(private val accountRepository: AccountRepository)