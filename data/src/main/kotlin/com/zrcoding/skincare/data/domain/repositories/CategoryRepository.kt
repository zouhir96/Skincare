package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Category

interface CategoryRepository {
    suspend fun getAll(): List<Category>

    suspend fun getOne(uuid: String): Category?
}