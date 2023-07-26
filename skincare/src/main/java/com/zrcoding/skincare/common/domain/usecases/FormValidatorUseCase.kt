package com.zrcoding.skincare.common.domain.usecases

import android.util.Patterns
import javax.inject.Inject

class FormValidatorUseCase @Inject constructor() {
    enum class Result {
        EMPTY, INVALID, VALID
    }

    fun validateEmail(email: String): Result = when {
        email.isBlank() -> Result.EMPTY
        Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Result.VALID
        else -> Result.INVALID
    }

    fun validatePassword(password: String): Result {
        if (password.isBlank()) return Result.EMPTY

        if (!Patterns.EMAIL_ADDRESS.matcher(password).matches()) return Result.INVALID

        return Result.VALID
    }
}