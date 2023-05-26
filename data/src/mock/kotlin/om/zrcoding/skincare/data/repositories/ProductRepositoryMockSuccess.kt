package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProductRepositoryMockSuccess @Inject constructor() : ProductRepository {

    private val favoriteProducts = MutableStateFlow<List<Product>>(emptyList())

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
    override suspend fun observeFavoritesProducts() = favoriteProducts
    override suspend fun deleteFromFavorites(uuid: String) {
        val otherProducts = favoriteProducts.value.filter { it.uuid != uuid }
        favoriteProducts.emit(otherProducts)
    }

    override suspend fun addToFavorites(uuid: String) {
        val alreadyExist = favoriteProducts.value.any { it.uuid == uuid }
        if (alreadyExist.not()) {
            val product = fakeProducts.first { it.uuid == uuid }
            val products = favoriteProducts.value.toMutableList().apply { add(product) }.toList()
            favoriteProducts.emit(products)
        }
    }
}