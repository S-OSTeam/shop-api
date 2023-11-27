package sosteam.deamhome.domain.item.resolver

import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.ninjasquad.springmockk.MockkBean
import org.springframework.graphql.test.tester.GraphQlTester
import sosteam.deamhome.domain.category.service.CategoryValidService

@OptIn(ExperimentalCoroutinesApi::class)
//@GraphQlTest(controllers = [CategoryQuery::class])
class CategoryQueryTest {

    lateinit var  graphQlTester: GraphQlTester

    @MockkBean
    lateinit var categoryValidServiceTest: CategoryValidService

//    @Test
//    fun 'get all itemCategories' () = runTest {
//
//    }

}