package com.zrcoding.skincare.features.connected.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<FavoritesScreenViewState>(FavoritesScreenViewState.Loading)
    val viewState: StateFlow<FavoritesScreenViewState> = _viewState

    init {
        viewModelScope.launch {
            productRepository.observeFavoritesProducts().collectLatest {
                _viewState.value = if (it.isEmpty()) {
                    FavoritesScreenViewState.EmptyWishList
                } else FavoritesScreenViewState.WishList(it)
            }
        }
    }

    fun onDeleteProduct(uuid: String) {
        viewModelScope.launch {
            productRepository.deleteFromFavorites(uuid)
        }
    }
}