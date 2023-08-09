package com.zrcoding.skincare.features.connected.product

import androidx.annotation.DrawableRes
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