package com.zrcoding.skincare.ui.auth.signup

import com.zrcoding.skincare.common.utils.StringUtils

data class SignUpScreenViewState(
    val email: String = StringUtils.EMPTY,
    val emailError: Int? = null,
    val password: String = StringUtils.EMPTY,
    val passwordValidationConstraints: PasswordValidationConstraints
    = PasswordValidationConstraints(),
    val isProcessing: Boolean = false
) {
    fun canSubmit() = emailError == null && passwordValidationConstraints.areAllValid()
}

data class PasswordValidationConstraints(
    val passwordConstraint8Characters: Boolean = false,
    val passwordConstraint1Uppercase: Boolean = false,
    val passwordConstraint1Symbol: Boolean = false,
    val passwordConstraint1Number: Boolean = false,
) {
    fun areAllValid() = passwordConstraint8Characters && passwordConstraint1Uppercase
            && passwordConstraint1Symbol && passwordConstraint1Number
}