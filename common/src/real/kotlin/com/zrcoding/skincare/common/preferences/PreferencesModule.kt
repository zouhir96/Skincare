package com.zrcoding.skincare.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<androidx.datastore.preferences.core.Preferences>
        by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    companion object {
        @Provides
        @Singleton
        fun provideUserDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<androidx.datastore.preferences.core.Preferences> {
            return applicationContext.settingsDataStore
        }
    }

    @Binds
    @Singleton
    abstract fun providePreferences(
        preferencesImpl: PreferencesImpl
    ): Preferences
}