package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.common.utils.add
import com.zrcoding.skincare.common.utils.remove
import com.zrcoding.skincare.common.utils.set
import com.zrcoding.skincare.data.domain.model.Cart
import com.zrcoding.skincare.data.domain.model.CartProduct
import com.zrcoding.skincare.data.domain.repositories.CartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CartRepositoryMockSuccess @Inject constructor() : CartRepository {

    companion object {
        var cart = Cart.Empty
        private val flowOfCart = MutableStateFlow(cart)
    }

    override fun observeCurrentCart(): Flow<Cart> = flowOfCart

    override suspend fun getCurrentCart(): Cart {
        delay(2000)
        return cart
    }

    override suspend fun addProduct(cartProduct: CartProduct) {
        delay(2000)
        cart = cart.copy(products = cart.products.add(cartProduct))
        flowOfCart.emit(cart)
    }

    override suspend fun removeProduct(cartProduct: CartProduct) {
        delay(2000)
        cart = cart.copy(products = cart.products.remove(cartProduct))
        flowOfCart.emit(cart)
    }

    override suspend fun incrementProductQuantity(cartProduct: CartProduct) {
        updateQuantity(cartProduct) { oldQte, newQte -> oldQte + newQte }
    }

    override suspend fun decrementProductQuantity(cartProduct: CartProduct) {
        updateQuantity(cartProduct) { oldQte, newQte -> oldQte - newQte }
    }

    private suspend fun updateQuantity(cartProduct: CartProduct, action: (Int, Int) -> Int) {
        delay(2000)
        val indexOf = cart.products.indexOf(cartProduct)
        val newProduct = cartProduct.copy(quantity = action(cartProduct.quantity, 1))

        cart = cart.copy(products = cart.products.set(indexOf, newProduct))
        flowOfCart.emit(cart)
    }
}