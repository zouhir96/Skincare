package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Cart
import com.zrcoding.skincare.data.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun observeCurrentCart(): Flow<Cart>

    suspend fun getCurrentCart(): Cart

    suspend fun addProduct(cartProduct: CartProduct)

    suspend fun removeProduct(cartProduct: CartProduct)

    suspend fun incrementProductQuantity(cartProduct: CartProduct)

    suspend fun decrementProductQuantity(cartProduct: CartProduct)
}