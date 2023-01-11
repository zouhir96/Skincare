package com.zrcoding.skincare.ui.product

import androidx.annotation.DrawableRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.zrcoding.skincare.R

data class ProductModel(
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val image: Int,
    val stars: Int,
)

val fakeProductList = listOf(
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
    ProductModel(
        name = "Toner",
        description = "Circumference Active Botanical Refining Toner",
        price = 60.00,
        image = R.drawable.skincare_products,
        stars = 4
    ),
)

@Composable
fun ProductScreen() {
    Text(text = "Product Screen")
}