package com.zrcoding.skincare.ui.product

import androidx.annotation.DrawableRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val image: Int,
    val stars: Int,
)

@Composable
fun ProductScreen() {
    Text(text = "Product Screen")
}