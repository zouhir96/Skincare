package com.zrcoding.skincare.ui.auth.signup

import com.zrcoding.skincare.common.utils.StringUtils

data class SignUpScreenViewState(
    val email: String = StringUtils.EMPTY,
    val emailError: Int? = null,
    val password: String = StringUtils.EMPTY,
    val passwordError: Int? = null,
    val confirmPassword: String = StringUtils.EMPTY,
    val confirmPasswordError: Int? = null,
    val isProcessing: Boolean = false
)