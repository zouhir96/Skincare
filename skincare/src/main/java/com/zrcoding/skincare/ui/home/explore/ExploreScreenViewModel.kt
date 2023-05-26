package com.zrcoding.skincare.ui.home.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.ui.common.Filter
import com.zrcoding.skincare.ui.common.filterAll
import com.zrcoding.skincare.ui.common.toFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<ExploreScreenViewState>(ExploreScreenViewState.Loading)
    val viewState: StateFlow<ExploreScreenViewState> = _viewState

    init {
        viewModelScope.launch {
            val filters = categoryRepository.getAll().toFilters()
            val products = productRepository.observeAll()

            val state = ExploreScreenViewState.ExploreState(
                filters = filters,
                products = products
            )
            _viewState.value = state
        }
    }

    fun onFilterChanged(filter: Filter) {
        viewModelScope.launch {
            val products = if (filter == filterAll) productRepository.observeAll()
            else productRepository.getCategoryProducts(filter.id)

            val state = (_viewState.value as ExploreScreenViewState.ExploreState).copy(
                selectedFilter = filter,
                products = products
            )
            _viewState.value = state
        }
    }

    fun onAddToFavorites(product: Product) {
        viewModelScope.launch {
            productRepository.addToFavorites(product.uuid)
        }
    }
}