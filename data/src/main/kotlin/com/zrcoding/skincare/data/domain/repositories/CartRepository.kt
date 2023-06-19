package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Cart
import com.zrcoding.skincare.data.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun observeCurrentCart(): Flow<Cart>

    suspend fun getCurrentCart(): Cart

    /**
     * Adds a product to the cart or updates the quantity if the same product with the same volume already exists.
     * @param cartProduct The CartProduct to add or update.
     */
    suspend fun addProduct(cartProduct: CartProduct)

    suspend fun removeProduct(cartProduct: CartProduct)

    suspend fun incrementProductQuantity(cartProduct: CartProduct)

    suspend fun decrementProductQuantity(cartProduct: CartProduct)

    /**
     * Applies a promo code and updates the cart with the promo code and amount.
     * @param code The promo code to apply.
     * @return The amount associated with the promo code.
     * @throws [PromoCodeException]
     */
    suspend fun applyPromoCodeAmount(code: String): Double

    /**
     * Loads the current cart and checkout it then reinitialize it
     * @return true for instance, maybe a payment link in the future
     */
    suspend fun checkoutCart(): Boolean
}