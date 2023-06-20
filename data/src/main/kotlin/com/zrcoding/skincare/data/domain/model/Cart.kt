package com.zrcoding.skincare.data.domain.model

import com.zrcoding.skincare.common.utils.StringUtils

const val MIN_CART_PRODUCT_QUANTITY = 1

data class Cart(
    val products: List<CartProduct> = emptyList(),
    val promoCode: PromoCode = PromoCode.Empty
) {
    companion object {
        val Empty = Cart(products = emptyList(), promoCode = PromoCode.Empty)
    }

    fun isEmpty() = products.isEmpty()

    fun productCount() = products.sumOf { it.quantity }

    fun subtotal() = products.sumOf { it.product.price * it.quantity }

    fun total() = subtotal() - promoCode.amount
}

data class CartProduct(
    val product: Product,
    val quantity: Int = MIN_CART_PRODUCT_QUANTITY,
    val volume: Volume? = null
)

data class PromoCode(
    val code: String,
    val amount: Double
) {
    companion object {
        val Empty = PromoCode(code = StringUtils.EMPTY, amount = 0.0)
    }
}