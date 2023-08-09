package com.zrcoding.skincare.common.domain.usecases

import androidx.core.util.PatternsCompat
import com.zrcoding.skincare.features.auth.signup.PasswordValidationConstraints
import javax.inject.Inject

class FormValidatorUseCase @Inject constructor() {
    enum class Result {
        EMPTY, INVALID, VALID
    }

    fun validateEmail(email: String): Result = when {
        email.isBlank() -> Result.EMPTY
        PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() -> Result.VALID
        else -> Result.INVALID
    }

    fun validatePassword(password: String): PasswordValidationConstraints {
        if (password.isBlank()) return PasswordValidationConstraints()
        val minCharactersConstrains = password.length >= 8
        val containsUppercase = password.contains("[A-Z]".toRegex())
        val containsSymbol = password.contains(
            "[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex()
        )
        val containsNumber = password.contains("[0-9]".toRegex())

        return PasswordValidationConstraints(
            passwordConstraint8Characters = minCharactersConstrains,
            passwordConstraint1Uppercase = containsUppercase,
            passwordConstraint1Symbol = containsSymbol,
            passwordConstraint1Number = containsNumber
        )
    }
}