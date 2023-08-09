package com.zrcoding.skincare.features.connected.cart

import com.zrcoding.skincare.data.domain.model.CartProduct

sealed interface CartScreenViewState {
    object Loading : CartScreenViewState
    data class Data(
        val products: List<CartProduct>,
        val promoCode: String,
        val promoCodeError: Int?,
        val productsCount: Int,
        val subtotal: Double,
        val shipping: Double,
        val promoCodeAmount: Double,
        val totalPayment: Double
    ) : CartScreenViewState

    object Empty : CartScreenViewState
    object GotoPayment : CartScreenViewState
}