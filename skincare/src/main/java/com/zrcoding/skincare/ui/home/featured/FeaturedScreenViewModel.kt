package com.zrcoding.skincare.ui.home.featured

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.common.utils.StringUtils.EMPTY
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.ui.common.domain.model.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedScreenViewModel @Inject constructor(
    private val requestUseCase: BuildFeaturedScreenViewStateRequestUseCase,
    private val viewStateUseCase: BuildFeaturedScreenViewStateUseCase,
    private val productRepository: ProductRepository
) : ViewModel() {

    private var searchTextCache: String = EMPTY
    private var selectedFilterCache: Filter? = null

    private val _viewState = MutableStateFlow(FeaturedScreenViewState())
    val viewState: StateFlow<FeaturedScreenViewState> = _viewState

    init {
        updateState()
    }

    fun onSearchStarted(searchText: String) {
        searchTextCache = searchText
        updateState()
    }

    fun onFilterChanged(filter: Filter) {
        selectedFilterCache = filter
        updateState()
    }

    fun onAddToFavorites(product: Product) {
        viewModelScope.launch {
            productRepository.addToFavorites(product.uuid)
        }
    }

    private fun updateState() {
        val request = requestUseCase(searchText = searchTextCache, filter = selectedFilterCache)
        viewModelScope.launch {
            _viewState.value = viewStateUseCase(request = request)
        }
    }
}