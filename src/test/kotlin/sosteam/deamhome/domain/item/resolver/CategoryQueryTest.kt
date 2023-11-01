package sosteam.deamhome.domain.item.resolver

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.context.annotation.Import
import sosteam.deamhome.domain.category.resolver.CategoryQuery
import org.springframework.graphql.test.tester.GraphQlTester
import sosteam.deamhome.domain.category.service.CategorySearchService

@OptIn(ExperimentalCoroutinesApi::class)
@GraphQlTest(controllers = [CategoryQuery::class])
class CategoryQueryTest {

    lateinit var  graphQlTester: GraphQlTester

    @MockkBean
    lateinit var categorySearchService: CategorySearchService

//    @Test
//    fun 'get all itemCategories' () = runTest {
//
//    }

}