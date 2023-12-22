package sosteam.deamhome.domain.naver.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.string.shouldMatch
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import sosteam.deamhome.global.security.provider.RandomKeyProvider

@SpringBootTest
internal class NaverLoginServiceTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var naverService: NaverService

    @MockkBean
    private lateinit var randomKeyProvider: RandomKeyProvider

    init {
        Given("url 패턴이 주어지면") {
            val expectedPattern =
                "https://nid\\.naver\\.com/oauth2\\.0/authorize\\?client_id=.*&response_type=code&redirect_uri=http://localhost:8080/api/naver/callback&state=.*"
            When("naverUrl을 생성하면") {
                every { randomKeyProvider.randomAlphabetNumber(32) } returns "randomState"

                val url = naverService.naverConnect(randomKeyProvider).naverUrl
                Then("url 패턴과 같은 형식이어야한다.") {
                    url shouldMatch Regex(expectedPattern)
                }
            }
        }
    }
}