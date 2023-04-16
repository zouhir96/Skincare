package com.zrcoding.skincare.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PreferencesImpl @Inject constructor() : Preferences {

    companion object {
        private val sharedPref = hashMapOf<String, Any>()
    }

    init {
        sharedPref[Preferences.ONBOARDING_COMPLETED_KEY] = false
    }

    override suspend fun storeString(key: String, value: String) {
        sharedPref[key] = value
    }

    override fun readString(key: String, defaultValue: String): Flow<String> {
        return flowOf(sharedPref.getOrElse(key) { return flowOf(defaultValue) } as String)
    }

    override suspend fun storeInt(key: String, value: Int) {
        sharedPref[key] = value
    }

    override fun readInt(key: String, defaultValue: Int): Flow<Int> {
        return flowOf(sharedPref.getOrElse(key) { return flowOf(defaultValue) } as Int)
    }

    override suspend fun storeBoolean(key: String, value: Boolean) {
        sharedPref[key] = value
    }

    override fun readBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        return flowOf(sharedPref.getOrElse(key) { return flowOf(defaultValue) } as Boolean)
    }
}