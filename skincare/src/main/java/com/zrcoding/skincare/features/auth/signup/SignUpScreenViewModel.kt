package com.zrcoding.skincare.features.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.domain.usecases.FormValidatorUseCase
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
class SignUpScreenViewModel @Inject constructor(
    private val formValidatorUseCase: FormValidatorUseCase,
    private val authenticationRepository: AuthenticationRepository,
    private val preferences: Preferences
) : ViewModel() {
    private val _viewState = MutableStateFlow(SignUpScreenViewState())
    val viewState: StateFlow<SignUpScreenViewState> = _viewState

    private val _isSignedUp = MutableSharedFlow<Unit>()
    val isSignedUp = _isSignedUp.asSharedFlow()

    fun onEmailTyped(email: String) {
        _viewState.update {
            it.copy(
                email = email,
                emailError = null
            )
        }
    }

    fun onPasswordTyped(password: String) {
        val passwordValidationConstraints = formValidatorUseCase.validatePassword(password)
        _viewState.update {
            it.copy(
                password = password,
                passwordValidationConstraints = passwordValidationConstraints
            )
        }
    }

    fun onSubmit() {
        val (email, password) = _viewState.value.let { Pair(it.email, it.password) }
        when (formValidatorUseCase.validateEmail(email)) {
            FormValidatorUseCase.Result.EMPTY -> R.string.common_required_field
            FormValidatorUseCase.Result.INVALID -> R.string.common_invalid_email
            FormValidatorUseCase.Result.VALID -> null
        }?.let { error ->
            _viewState.update { it.copy(emailError = error) }
            return
        }
        _viewState.update { it.copy(isProcessing = true) }
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                val token = authenticationRepository.signup(email = email, password = password)
                preferences.storeString(Preferences.ACCESS_TOKEN_KEY, token.accessToken)
                preferences.storeString(Preferences.REFRESH_TOKEN_KEY, token.refreshToken)
                _viewState.update { it.copy(isProcessing = false) }
                _isSignedUp.emit(Unit)
            } catch (exception: Exception) {
                _viewState.update { it.copy(isProcessing = false) }
            }
        }
    }
}
