package com.zrcoding.skincare.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.CartProduct
import com.zrcoding.skincare.data.domain.repositories.CartRepository
import com.zrcoding.skincare.data.sources.remote.exceptions.PromoCodeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<CartScreenViewState>(CartScreenViewState.Loading)
    val viewState: StateFlow<CartScreenViewState> = _viewState

    init {
        viewModelScope.launch {
            cartRepository.observeCurrentCart().collectLatest {
                _viewState.value = if (it.isEmpty()) {
                    CartScreenViewState.Empty
                } else with(it) {
                    CartScreenViewState.Data(
                        products = products,
                        promoCode = promoCode.code,
                        promoCodeError = null,
                        productsCount = productCount(),
                        subtotal = subtotal(),
                        shipping = 0.0,
                        promoCodeAmount = promoCode.amount,
                        totalPayment = total()
                    )
                }
            }
        }
    }

    fun incrementProductQte(cartProduct: CartProduct) {
        viewModelScope.launch(context = Dispatchers.IO) {
            cartRepository.incrementProductQuantity(cartProduct)
        }
    }

    fun decrementProductQte(cartProduct: CartProduct) {
        viewModelScope.launch(context = Dispatchers.IO) {
            cartRepository.decrementProductQuantity(cartProduct)
        }
    }

    fun onRemoveProduct(cartProduct: CartProduct) {
        viewModelScope.launch(context = Dispatchers.IO) {
            cartRepository.removeProduct(cartProduct)
        }
    }

    fun onPromoCodeTyped(code: String) {
        with(_viewState) {
            value = (value as CartScreenViewState.Data).copy(
                promoCode = code,
                promoCodeError = null
            )
        }
    }

    fun applyPromoCode() {
        val data = _viewState.value as CartScreenViewState.Data
        val promoCode = data.promoCode
        if (promoCode.isBlank()) {
            _viewState.value = data.copy(promoCodeError = R.string.common_required_field)
            return
        }
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                cartRepository.applyPromoCodeAmount(code = promoCode)
            } catch (error: Throwable) {
                val errorMsg = when (error) {
                    PromoCodeException.Expired -> R.string.cart_promo_code_expired
                    PromoCodeException.Invalid -> R.string.cart_promo_code_invalid
                    else -> R.string.common_network_error_with_try_later
                }
                _viewState.value = data.copy(promoCodeError = errorMsg)
            }
        }
    }

    fun onCheckoutCart() {
        viewModelScope.launch {
            val checkoutResult = cartRepository.checkoutCart()
            if (checkoutResult) {
                _viewState.value = CartScreenViewState.GotoPayment
            }
        }
    }
}