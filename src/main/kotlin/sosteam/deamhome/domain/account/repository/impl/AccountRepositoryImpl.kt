//package sosteam.deamhome.domain.account.repository.impl
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.reactive.asFlow
//import lombok.RequiredArgsConstructor
//import sosteam.deamhome.domain.account.entity.Account
//import sosteam.deamhome.domain.account.entity.QAccount
//import sosteam.deamhome.domain.account.repository.custom.AccountRepositoryCustom
//import sosteam.deamhome.domain.account.repository.querydsl.AccountQueryDslRepository
//import java.time.LocalDateTime
//
//@RequiredArgsConstructor
//class AccountRepositoryImpl(
//	private val querydsl: AccountQueryDslRepository,
//) : AccountRepositoryCustom {
//	private val account = QAccount.account
//
//	override fun getDormantAccount(): Flow<Account> {
//		return querydsl.findAll(account.loginAt.before(LocalDateTime.now().minusYears(1))).asFlow()
//	}
//
//}