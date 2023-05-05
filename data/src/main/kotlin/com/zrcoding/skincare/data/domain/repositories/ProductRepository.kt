package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAll(): Flow<List<Product>>

    suspend fun getOne(uuid: String): Product?

    suspend fun searchProduct(searchText: String): List<Product>

    suspend fun searchProduct(searchText: String, limit: Int): List<Product>

    suspend fun getCategoryProducts(categoryUuid: String): List<Product>

    suspend fun searchCategoryProducts(searchText: String, categoryUuid: String): List<Product>

    suspend fun getMostPopularProducts(limit: Int): List<Product>

    suspend fun getMostPopularProducts(limit: Int, categoryUuid: String): List<Product>

    suspend fun getNewestProduct(): Product?
}