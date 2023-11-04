package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountValidService(private val accountRepository: AccountRepository)