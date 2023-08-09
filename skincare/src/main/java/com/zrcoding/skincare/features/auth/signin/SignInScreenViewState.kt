package com.zrcoding.skincare.features.auth.signin

import com.zrcoding.skincare.common.utils.StringUtils

data class SignInScreenViewState(
    val email: String = StringUtils.EMPTY,
    val emailError: Int? = null,
    val password: String = StringUtils.EMPTY,
    val passwordError: Int? = null,
    val isProcessing: Boolean = false
)

sealed interface SignInUiEvents {
    object LoggedIn : SignInUiEvents
}