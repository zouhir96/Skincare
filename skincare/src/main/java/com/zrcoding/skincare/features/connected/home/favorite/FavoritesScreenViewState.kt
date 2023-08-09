package com.zrcoding.skincare.features.connected.home.favorite

import com.zrcoding.skincare.data.domain.model.Product

sealed interface FavoritesScreenViewState {
    object Loading : FavoritesScreenViewState
    object EmptyWishList : FavoritesScreenViewState
    data class WishList(val wishList: List<Product>) : FavoritesScreenViewState
}
