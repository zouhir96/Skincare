package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.data.domain.model.Category
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.model.Volume
import com.zrcoding.skincare.data.domain.model.VolumeUnit
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.ui.common.Filter
import com.zrcoding.skincare.ui.common.filterAll
import com.zrcoding.skincare.ui.common.toFilter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.UUID

@RunWith(JUnit4::class)
class BuildFeaturedScreenViewStateUseCaseTest {

    private val maxDisplayedProducts = 2

    private val fakeVolumes = listOf(
        Volume(
            UUID.randomUUID().toString(), 50, VolumeUnit.ML
        ), Volume(
            UUID.randomUUID().toString(), 100, VolumeUnit.ML
        ), Volume(
            UUID.randomUUID().toString(), 150, VolumeUnit.ML
        )
    )

    private val fakeCategories = listOf(
        Category(
            uuid = UUID.randomUUID().toString(),
            name = "Handbody",
        ),
        Category(
            uuid = UUID.randomUUID().toString(),
            name = "Serum",
        ),
        Category(
            uuid = UUID.randomUUID().toString(),
            name = "Toner",
        ),
        Category(
            uuid = UUID.randomUUID().toString(),
            name = "Cleanser",
        ),
    )

    private val fakeProducts = listOf(
        Product(
            uuid = UUID.randomUUID().toString(),
            name = "Circumference Active Botanical Refining Toner",
            title = "Circumference Active Botanical Refining Toner",
            description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
            price = 60.0,
            stock = 100,
            stars = 5,
            reviews = 100,
            imagesUrls = listOf(
                "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
                "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
                "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
            ),
            volumes = fakeVolumes,
            category = fakeCategories[0]
        ), Product(
            uuid = UUID.randomUUID().toString(),
            name = "Protect Available Exclusively from a Skincare",
            title = "Protect Available Exclusively from a Skincare",
            description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
            price = 100.0,
            stock = 100,
            stars = 5,
            reviews = 100,
            imagesUrls = listOf(
                "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
                "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
                "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
            ),
            volumes = fakeVolumes,
            category = fakeCategories[1]
        ), Product(
            uuid = UUID.randomUUID().toString(),
            name = "Environ package skin EssentiA*",
            title = "Environ package skin EssentiA*",
            description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
            price = 190.0,
            stock = 100,
            stars = 5,
            reviews = 100,
            imagesUrls = listOf(
                "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
                "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
                "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
            ),
            volumes = fakeVolumes,
            category = fakeCategories[2]
        )
    )

    private val baseViewState =
        FeaturedScreenViewState(filters = fakeCategories.map { it.toFilter() }.toMutableList()
            .apply { add(0, filterAll) },
            selectedFilter = filterAll,
            newestProduct = fakeProducts.maxByOrNull { it.price })

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    lateinit var useCase: BuildFeaturedScreenViewStateUseCase

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this)
    }

    @Test
    fun initialState() = runTest {
        // GIVEN
        val expectedProducts = fakeProducts.take(maxDisplayedProducts)
        coEvery { productRepository.getMostPopularProducts(maxDisplayedProducts) } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns fakeProducts.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns fakeCategories
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
        val expectedProducts = fakeProducts.take(maxDisplayedProducts)
        coEvery {
            productRepository.searchProduct(
                searchText,
                maxDisplayedProducts
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns fakeProducts.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns fakeCategories
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
        val expectedProducts = fakeProducts.take(maxDisplayedProducts)
        coEvery {
            productRepository.getMostPopularProducts(
                maxDisplayedProducts,
                selectedFilter.id
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns fakeProducts.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns fakeCategories
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
        val expectedProducts = fakeProducts.take(maxDisplayedProducts)
        coEvery {
            productRepository.searchCategoryProducts(
                searchText = searchText,
                categoryUuid = selectedFilter.id,
                limit = maxDisplayedProducts
            )
        } returns expectedProducts
        coEvery { productRepository.getNewestProduct() } returns fakeProducts.maxByOrNull { it.price }
        coEvery { categoryRepository.getAll() } returns fakeCategories
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