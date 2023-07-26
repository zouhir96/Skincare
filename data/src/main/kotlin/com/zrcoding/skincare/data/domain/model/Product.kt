package com.zrcoding.skincare.data.domain.model

import java.util.UUID

data class Product(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    val title: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val stars: Int,
    val reviews: Int,
    val imagesUrls: List<String>,
    val volumes: List<Volume>,
    val category: Category
)


