package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.PwdAndConfirmPwdNotMachedException
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
@RequiredArgsConstructor
class AccountChangePwdService(
    val accountRepository: AccountRepository,
    val accountValidService: AccountValidService,
    val passwordEncoder: PasswordEncoder
) {
    suspend fun changePwd (
        email: String,
        newPwd: String,
        confirmPwd: String
    ): String {
        if(newPwd != confirmPwd) throw PwdAndConfirmPwdNotMachedException()

        val account = accountRepository.findAccountByEmail(email)
            ?: throw AccountNotFoundException()
        account.pwd = passwordEncoder.encode(newPwd)

        accountRepository.save(account).awaitSingle()
        return account.userId
    }
}