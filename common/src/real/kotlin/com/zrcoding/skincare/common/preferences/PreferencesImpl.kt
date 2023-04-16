package com.zrcoding.skincare.common.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesImpl @Inject constructor(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) : Preferences {
    override suspend fun storeString(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[stringKey] = value
        }
    }

    override fun readString(key: String, defaultValue: String): Flow<String> {
        val stringKey = stringPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[stringKey] ?: defaultValue
        }
    }

    override suspend fun storeInt(key: String, value: Int) {
        val stringKey = intPreferencesKey(key)
        dataStore.edit { settings ->
            settings[stringKey] = value
        }
    }

    override fun readInt(key: String, defaultValue: Int): Flow<Int> {
        val stringKey = intPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[stringKey] ?: defaultValue
        }
    }

    override suspend fun storeBoolean(key: String, value: Boolean) {
        val stringKey = booleanPreferencesKey(key)
        dataStore.edit { settings ->
            settings[stringKey] = value
        }
    }

    override fun readBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        val stringKey = booleanPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[stringKey] ?: defaultValue
        }
    }
}