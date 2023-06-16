package com.zrcoding.skincare.ui.home.featured

import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.ui.common.domain.model.Filter
import com.zrcoding.skincare.ui.common.domain.model.filterAll
import com.zrcoding.skincare.ui.common.domain.model.toFilters
import javax.inject.Inject

class BuildFeaturedScreenViewStateUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {
    sealed interface Request {
        object Initial : Request
        data class SearchOnly(val text: String) : Request
        data class FilterOnly(val filter: Filter) : Request
        data class SearchFiltered(val text: String, val filter: Filter) : Request
    }

    private val maxDisplayedProducts = 2
    private var filtersCache: List<Filter> = emptyList()
    private var newestProductCache: Product? = null

    suspend operator fun invoke(request: Request): FeaturedScreenViewState {
        if (filtersCache.isEmpty()) {
            filtersCache = categoryRepository.getAll().toFilters()
        }

        if (newestProductCache == null) {
            newestProductCache = productRepository.getNewestProduct()
        }

        val viewState = FeaturedScreenViewState(
            filters = filtersCache,
            selectedFilter = filterAll,
            newestProduct = newestProductCache
        )


        return when (request) {
            Request.Initial -> viewState.copy(
                products = productRepository.getMostPopularProducts(limit = maxDisplayedProducts)
            )

            is Request.SearchOnly -> viewState.copy(
                searchText = request.text,
                products = productRepository.searchProduct(
                    searchText = request.text,
                    limit = maxDisplayedProducts
                )
            )

            is Request.FilterOnly -> viewState.copy(
                products = productRepository.getMostPopularProducts(
                    limit = maxDisplayedProducts,
                    categoryUuid = request.filter.id
                ),
                selectedFilter = request.filter
            )

            is Request.SearchFiltered -> viewState.copy(
                searchText = request.text,
                products = productRepository.searchCategoryProducts(
                    searchText = request.text,
                    categoryUuid = request.filter.id,
                    limit = maxDisplayedProducts
                ),
                selectedFilter = request.filter
            )
        }
    }
}