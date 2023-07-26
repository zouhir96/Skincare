package com.zrcoding.skincare.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.preferences.Preferences
import com.zrcoding.skincare.data.domain.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val preferences: Preferences,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SignInScreenViewState())
    val viewState: StateFlow<SignInScreenViewState> = _viewState

    private val _isLoggedIn = MutableSharedFlow<Unit>()
    val isLoggedIn = _isLoggedIn.asSharedFlow()

    fun onEmailTyped(text: String) {
        _viewState.update {
            it.copy(email = text, emailError = null, passwordError = null)
        }
    }

    fun onPasswordTyped(text: String) {
        _viewState.update {
            it.copy(emailError = null, password = text, passwordError = null)
        }
    }

    fun onSubmit() {
        val email = viewState.value.email
        if (email.isBlank()) {
            _viewState.update { it.copy(emailError = R.string.common_required_field) }
            return
        }
        val password = viewState.value.password
        if (password.isBlank()) {
            _viewState.update { it.copy(passwordError = R.string.common_required_field) }
            return
        }
        viewModelScope.launch(context = Dispatchers.IO) {
            _viewState.update { it.copy(isProcessing = true) }
            try {
                val token = authenticationRepository.login(email = email, password = password)
                preferences.storeString(Preferences.ACCESS_TOKEN_KEY, token.accessToken)
                preferences.storeString(Preferences.REFRESH_TOKEN_KEY, token.refreshToken)
                _viewState.update { it.copy(isProcessing = false) }
                _isLoggedIn.emit(Unit)
            } catch (exception: Exception) {
                _viewState.update { it.copy(isProcessing = false) }
            }
        }
    }
}