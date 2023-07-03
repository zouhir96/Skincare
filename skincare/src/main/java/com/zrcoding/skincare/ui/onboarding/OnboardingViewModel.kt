package com.zrcoding.skincare.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.common.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome: SharedFlow<Unit> = _navigateToHome

    fun onOnboardingCompleted() {
        viewModelScope.launch {
            preferences.storeBoolean(key = Preferences.ONBOARDING_COMPLETED_KEY, value = true)
            _navigateToHome.emit(Unit)
        }
    }
}