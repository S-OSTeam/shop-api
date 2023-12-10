package sosteam.deamhome.global

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester

@SpringBootTest
@AutoConfigureGraphQlTester
abstract class ResolverBaseTest {
	@Autowired
	private lateinit var graphQlTester: GraphQlTester
}