package sosteam.deamhome.global.category

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import sosteam.deamhome.global.category.entity.CategoryEntity
import sosteam.deamhome.global.category.exception.AlreadyExistCategoryException
import sosteam.deamhome.global.category.exception.MaxDepthExceedException
import sosteam.deamhome.global.category.handler.response.CategoryTreeResponse
import sosteam.deamhome.global.category.provider.CategoryProvider
import sosteam.deamhome.global.category.respository.CategoryRepository

class CategoryProviderTest : BehaviorSpec({

    val repository = mockk<CategoryRepository<CategoryEntity>>()
    val categoryProvider = CategoryProvider(repository)

    Given("a valid category entity") {
        val category = object : CategoryEntity(2) {
            override var publicId: String = "testPublicId"
            override var parentPublicId: String = "parentPublicId"
            override var title: String = "Test Category"
        }

        When("saving the category") {
            coEvery { repository.save(category) } returns category

            Then("it should return the saved category") {
                val savedCategory = categoryProvider.saveCategory(category)
                savedCategory.title shouldBe "Test Category"
            }
        }
    }

    Given("a category entity representing the top category") {
        val topCategory = object : CategoryEntity(2) {
            override var publicId: String = "topPublicId"
            override var parentPublicId: String = "topPublicId"
            override var title: String = "Top Category"
        }

        When("validating the top category") {
            coEvery { repository.findByTitle(topCategory.title) } returns emptyFlow()

            Then("it should not throw any exception") {
                shouldNotThrow<AlreadyExistCategoryException> {
                    categoryProvider.validateCategory(topCategory)
                }
            }
        }

        When("validating the top category") {
            coEvery { repository.findByTitle(topCategory.title) } returns flowOf(topCategory)

            Then("it should throw AlreadyExistCategoryException") {
                shouldThrow<AlreadyExistCategoryException> {
                    categoryProvider.validateCategory(topCategory)
                }
            }
        }

    }

    Given("a valid category entity with a parent category") {
        val category = object : CategoryEntity(2) {
            override var publicId: String = "testPublicId"
            override var parentPublicId: String = "parentPublicId"
            override var title: String = "Test Category"
        }

        val parentCategory = object : CategoryEntity(2) {
            override var publicId: String = "parentPublicId"
            override var parentPublicId: String = "parentPublicId"
            override var title: String = "Parent Category"
        }

        When("validating the parent category") {
            coEvery { repository.findByPublicId(parentCategory.publicId) } returns parentCategory
            coEvery { repository.findByParentPublicIdAndTitle(category.parentPublicId, category.title) } returns null

            Then("it should not throw any exception") {
                shouldNotThrow<AlreadyExistCategoryException> {
                    categoryProvider.validateCategory(category)
                }
            }
        }

        When("validating the parent category with the same title") {
            coEvery { repository.findByPublicId(parentCategory.publicId) } returns parentCategory
            coEvery { repository.findByParentPublicIdAndTitle(category.parentPublicId, category.title) } returns category

            Then("it should throw AlreadyExistCategoryException") {
                shouldThrow<AlreadyExistCategoryException> {
                    categoryProvider.validateCategory(category)
                }
            }
        }

        When("validating the parent category with max depth exceeded") {

            val parentCategory = object : CategoryEntity(2) {
                override var publicId: String = "parentPublicId"
                override var parentPublicId: String = "grandParentPublicId"
                override var title: String = "Parent Category"
            }

            val grandParentCategory = object : CategoryEntity(2) {
                override var publicId: String = "grandParentPublicId"
                override var parentPublicId: String = "grandGrandParentPublicId"
                override var title: String = "Grand Parent Category"
            }

            val grandGrandParentCategory = object : CategoryEntity(2) {
                override var publicId: String = "grandGrandParentPublicId"
                override var parentPublicId: String = "grandGrandParentPublicId"
                override var title: String = "Grand Grand Parent Category"
            }

            coEvery { repository.findByPublicId(parentCategory.publicId) } returns parentCategory
            coEvery { repository.findByPublicId(grandParentCategory.publicId) } returns grandParentCategory
            coEvery { repository.findByPublicId(grandGrandParentCategory.publicId) } returns grandGrandParentCategory
            coEvery { repository.findByParentPublicIdAndTitle(category.parentPublicId, category.title) } returns null

            Then("it should throw MaxDepthExceedException") {
                shouldThrow<MaxDepthExceedException> {
                    categoryProvider.validateCategory(category)
                }
            }
        }
    }


    Given("a list of categories") {
        val category1 = object : CategoryEntity(2) {
            override var publicId: String = "publicId1"
            override var parentPublicId: String = "publicId1"
            override var title: String = "Category 1"
        }
        val category2 = object : CategoryEntity(2) {
            override var publicId: String = "publicId2"
            override var parentPublicId: String = "publicId2"
            override var title: String = "Category 2"
        }
        val category3 = object : CategoryEntity(2) {
            override var publicId: String = "publicId3"
            override var parentPublicId: String = "publicId1"
            override var title: String = "Category 3"
        }

        When("finding all categories tree") {
            coEvery { repository.findAll() } returns flowOf(category1, category2, category3)

            val fromCategory: (CategoryEntity) -> CategoryTreeResponse<CategoryEntity> = { category ->
                object : CategoryTreeResponse<CategoryEntity>() {
                    override val publicId: String = category.publicId
                    override val title: String = category.title
                    override val children = mutableListOf<CategoryTreeResponse<CategoryEntity>>()
                }
            }

            val categoryTreeResponses = categoryProvider.findAllCategoriesTree(fromCategory)

            Then("it should contain all root categories") {
                categoryTreeResponses.shouldHaveSize(2)
                categoryTreeResponses.map { it.publicId }.shouldContain(category1.publicId)
                categoryTreeResponses.map { it.publicId }.shouldContain(category2.publicId)
            }

            Then("it should have correct children for each category") {
                val rootCategory = categoryTreeResponses.first { it.publicId == category1.publicId }
                rootCategory.children.shouldHaveSize(1)
                rootCategory.children.map { it.publicId }.shouldContain(category3.publicId)
            }
        }
    }

})