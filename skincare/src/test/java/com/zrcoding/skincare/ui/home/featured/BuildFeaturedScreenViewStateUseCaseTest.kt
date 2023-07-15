package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.common.domain.model.filterAll
import com.zrcoding.skincare.common.domain.model.toFilter
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.data.sources.fake.fakeCategories
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.ui.base.BaseTest
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class BuildFeaturedScreenViewStateUseCaseTest : BaseTest() {

    private val maxDisplayedProducts = 2

    private val categories = fakeCategories.take(4)

    private val products = fakeProducts.take(4)

    private val baseViewState =
        FeaturedScreenViewState(filters = categories.map { it.toFilter() }.toMutableList()
            .apply { add(0, filterAll) },
            selectedFilter = filterAll,
            newestProduct = products.maxByOrNull { it.price })

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    lateinit var useCase: BuildFeaturedScreenViewStateUseCase

    @Test
    fun initialState() = runTest {
        // GIVEN
        val expectedProducts = products.take(maxDisplayedProducts)
        coEvery { productRepository.getMostPopularProducts(maxDisplayedProducts) } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns products.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns categories
        val request = BuildFeaturedScreenViewStateUseCase.Request.Initial

        // WHEN
        val expectedViewState = baseViewState.copy(products = expectedProducts)
        val result = useCase.invoke(request)

        // THEN
        assertEquals(expectedViewState, result)
        coVerifyOrder {
            categoryRepository.getAll()
            productRepository.getNewestProduct()
            productRepository.getMostPopularProducts(maxDisplayedProducts)
        }
    }

    @Test
    fun `search only`() = runTest {
        // GIVEN
        val searchText = "someTypedText"
        val expectedProducts = products.take(maxDisplayedProducts)
        coEvery {
            productRepository.searchProduct(
                searchText,
                maxDisplayedProducts
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns products.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns categories
        val request = BuildFeaturedScreenViewStateUseCase.Request.SearchOnly(searchText)

        // WHEN
        val expectedViewState =
            baseViewState.copy(searchText = searchText, products = expectedProducts)
        val result = useCase.invoke(request)

        // THEN
        assertEquals(expectedViewState, result)
        coVerifyOrder {
            categoryRepository.getAll()
            productRepository.getNewestProduct()
            productRepository.searchProduct(searchText, maxDisplayedProducts)
        }
    }

    @Test
    fun `filter only`() = runTest {
        // GIVEN
        val selectedFilter = Filter(UUID.randomUUID().toString(), "")
        val expectedProducts = products.take(maxDisplayedProducts)
        coEvery {
            productRepository.getMostPopularProducts(
                maxDisplayedProducts,
                selectedFilter.id
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns products.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns categories
        val request = BuildFeaturedScreenViewStateUseCase.Request.FilterOnly(selectedFilter)

        // WHEN
        val expectedViewState = baseViewState.copy(
            products = expectedProducts,
            selectedFilter = selectedFilter
        )
        val result = useCase.invoke(request)

        // THEN
        assertEquals(expectedViewState, result)
        coVerifyOrder {
            categoryRepository.getAll()
            productRepository.getNewestProduct()
            productRepository.getMostPopularProducts(maxDisplayedProducts, selectedFilter.id)
        }
    }

    @Test
    fun `search filtered`() = runTest {
        // GIVEN
        val searchText = "someTypedText"
        val selectedFilter = Filter(UUID.randomUUID().toString(), "")
        val expectedProducts = products.take(maxDisplayedProducts)
        coEvery {
            productRepository.searchCategoryProducts(
                searchText = searchText,
                categoryUuid = selectedFilter.id,
                limit = maxDisplayedProducts
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns products.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns categories
        val request = BuildFeaturedScreenViewStateUseCase.Request.SearchFiltered(
            text = searchText,
            filter = selectedFilter
        )

        // WHEN
        val expectedViewState = baseViewState.copy(
            searchText = searchText,
            products = expectedProducts,
            selectedFilter = selectedFilter
        )
        val result = useCase.invoke(request)

        // THEN
        assertEquals(expectedViewState, result)
        coVerifyOrder {
            categoryRepository.getAll()
            productRepository.getNewestProduct()
            productRepository.searchCategoryProducts(
                searchText = searchText,
                categoryUuid = selectedFilter.id,
                limit = maxDisplayedProducts
            )
        }
    }
}