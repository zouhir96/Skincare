package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class ProductRepositoryMockSuccess @Inject constructor() : ProductRepository {

    override fun getAll() = flowOf(fakeProducts)

    override suspend fun getOne(uuid: String) = fakeProducts.find { it.uuid == uuid }
    override suspend fun searchProduct(searchText: String) = fakeProducts.filter {
        it.name.contains(searchText) || it.title.contains(searchText)
    }

    override suspend fun searchProduct(searchText: String, limit: Int) =
        searchProduct(searchText).take(limit)

    override suspend fun getCategoryProducts(categoryUuid: String) = fakeProducts.filter {
        it.category.uuid == categoryUuid
    }

    override suspend fun searchCategoryProducts(
        searchText: String,
        categoryUuid: String
    ) = fakeProducts.filter {
        it.category.uuid == categoryUuid && (it.name.contains(searchText) || it.title.contains(
            searchText
        ))
    }

    override suspend fun searchCategoryProducts(
        searchText: String,
        categoryUuid: String,
        limit: Int
    ) = fakeProducts.filter {
        it.category.uuid == categoryUuid && (it.name.contains(searchText) || it.title.contains(
            searchText
        ))
    }.take(limit)

    override suspend fun getMostPopularProducts(limit: Int) = fakeProducts.take(limit)

    override suspend fun getMostPopularProducts(
        limit: Int,
        categoryUuid: String
    ) = getCategoryProducts(categoryUuid).take(limit)

    override suspend fun getNewestProduct(): Product? = fakeProducts.maxByOrNull { it.price }
}

val fakeProducts = listOf(
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[1]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[2]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[3]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[1]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[2]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[3]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stars = 5,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    )
)