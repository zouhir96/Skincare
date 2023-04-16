package com.zrcoding.skincare.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.common.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    fun onOnboardingCompleted() {
        viewModelScope.launch {
            preferences.storeBoolean(key = Preferences.ONBOARDING_COMPLETED_KEY, value = true)
        }
    }
}