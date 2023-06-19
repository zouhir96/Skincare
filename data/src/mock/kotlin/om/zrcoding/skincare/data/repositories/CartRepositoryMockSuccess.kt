package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.common.utils.add
import com.zrcoding.skincare.common.utils.remove
import com.zrcoding.skincare.common.utils.set
import com.zrcoding.skincare.data.domain.model.Cart
import com.zrcoding.skincare.data.domain.model.CartProduct
import com.zrcoding.skincare.data.domain.model.PromoCode
import com.zrcoding.skincare.data.domain.repositories.CartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CartRepositoryMockSuccess @Inject constructor() : CartRepository {

    companion object {
        var cart = Cart.Empty
        private val flowOfCart = MutableStateFlow(cart)
    }

    override fun observeCurrentCart(): Flow<Cart> = flowOfCart.apply { update { cart } }

    override suspend fun getCurrentCart(): Cart {
        delay(2000)
        return cart
    }

    override suspend fun addProduct(cartProduct: CartProduct) {
        val indexOfExistingProduct = cart.products.indexOfFirst {
            it.product == cartProduct.product && it.volume == cartProduct.volume
        }
        val newProducts = if (indexOfExistingProduct >= 0) {
            val existingProduct = cart.products[indexOfExistingProduct]
            val updatedProduct = existingProduct.copy(
                quantity = existingProduct.quantity + cartProduct.quantity
            )
            cart.products.set(indexOfExistingProduct, updatedProduct)
        } else {
            cart.products.add(cartProduct)
        }
        cart = cart.copy(products = newProducts)
        flowOfCart.emit(cart)
    }

    override suspend fun removeProduct(cartProduct: CartProduct) {
        cart = cart.copy(products = cart.products.remove(cartProduct))
        flowOfCart.emit(cart)
    }

    override suspend fun incrementProductQuantity(cartProduct: CartProduct) {
        updateQuantity(cartProduct) { oldQte, newQte -> oldQte + newQte }
    }

    override suspend fun decrementProductQuantity(cartProduct: CartProduct) {
        updateQuantity(cartProduct) { oldQte, newQte -> oldQte - newQte }
    }

    override suspend fun applyPromoCodeAmount(code: String): Double {
        val promoCodeAmount = 10.0
        cart = cart.copy(promoCode = PromoCode(code, promoCodeAmount))
        flowOfCart.emit(cart)
        return promoCodeAmount
    }

    override suspend fun checkoutCart(): Boolean {
        cart = Cart.Empty
        return true
    }

    private suspend fun updateQuantity(cartProduct: CartProduct, action: (Int, Int) -> Int) {
        val indexOf = cart.products.indexOf(cartProduct)
        val newProduct = cartProduct.copy(quantity = action(cartProduct.quantity, 1))

        cart = cart.copy(products = cart.products.set(indexOf, newProduct))
        flowOfCart.emit(cart)
    }
}