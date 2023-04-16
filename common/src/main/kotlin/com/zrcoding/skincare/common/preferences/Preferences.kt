package com.zrcoding.skincare.common.preferences

import com.zrcoding.skincare.common.utils.StringUtils
import kotlinx.coroutines.flow.Flow

interface Preferences {
    companion object {
        const val ONBOARDING_COMPLETED_KEY = "tutorial_completed_key"
    }

    suspend fun storeString(key: String, value: String)

    fun readString(key: String, defaultValue: String = StringUtils.EMPTY): Flow<String>

    suspend fun storeInt(key: String, value: Int)

    fun readInt(key: String, defaultValue: Int): Flow<Int>

    suspend fun storeBoolean(key: String, value: Boolean)

    fun readBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean>
}