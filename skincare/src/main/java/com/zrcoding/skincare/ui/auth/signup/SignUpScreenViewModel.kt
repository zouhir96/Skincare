package com.zrcoding.skincare.ui.auth.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(SignUpScreenViewState())
    val viewState: StateFlow<SignUpScreenViewState> = _viewState

    fun onEmailTyped(email: String) {
        TODO("Not yet implemented")
    }

    fun onPasswordTyped(password: String) {
        TODO("Not yet implemented")
    }

    fun onConfirmPasswordTyped(confirmPassword: String) {
        TODO("Not yet implemented")
    }

    fun onSubmit() {
        TODO("Not yet implemented")
    }
}
