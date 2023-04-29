package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAll(): Flow<List<Product>>

    suspend fun getOne(uuid: String): Product?
}