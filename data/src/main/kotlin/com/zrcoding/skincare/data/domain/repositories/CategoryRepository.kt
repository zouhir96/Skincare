package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAll(): Flow<List<Category>>

    suspend fun getOne(uuid: String): Category?
}