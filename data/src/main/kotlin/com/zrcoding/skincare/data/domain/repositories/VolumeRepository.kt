package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Volume
import kotlinx.coroutines.flow.Flow

interface VolumeRepository {
    fun getAll(): Flow<List<Volume>>

    suspend fun getOne(uuid: String): Volume?
}