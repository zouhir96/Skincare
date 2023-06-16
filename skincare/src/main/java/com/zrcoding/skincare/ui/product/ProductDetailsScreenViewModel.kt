package com.zrcoding.skincare.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ProductDetailsScreenViewState>(ProductDetailsScreenViewState.Loading)
    val viewState: StateFlow<ProductDetailsScreenViewState> = _viewState

    fun getProductDetails(uuid: String) {
        viewModelScope.launch {
            _viewState.value = productRepository.getOne(uuid)?.let {
                ProductDetailsScreenViewState.Details(it)
            } ?: ProductDetailsScreenViewState.Error
        }
    }

    fun onAddToFavorites(uuid: String) {
        viewModelScope.launch {
            productRepository.addToFavorites(uuid)
        }
    }
}