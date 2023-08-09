package com.zrcoding.skincare.features.connected.product

import com.zrcoding.skincare.data.domain.model.Product

sealed interface ProductDetailsScreenViewState {
    object Loading : ProductDetailsScreenViewState
    data class Details(val product: Product) : ProductDetailsScreenViewState
    object Error : ProductDetailsScreenViewState
}