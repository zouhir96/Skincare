package com.zrcoding.skincare.ui.auth.completaccount

import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.GENDER

const val AGE_MIN_VALUE = 15
const val AGE_MAX_VALUE = 90
const val AGE_MAX_STEPS = 75

data class CompleteAccountScreenViewState(
    val fullName: String = "",
    val age: Int = AGE_MIN_VALUE,
    val gender: GENDER = GENDER.FEMALE
) {
    fun canSubmit() = fullName.isNotBlank() && age >= AGE_MIN_VALUE && age <= AGE_MAX_VALUE
}

fun GENDER.getNameResId() = when (this) {
    GENDER.FEMALE -> R.string.complete_account_gender_woman
    GENDER.MALE -> R.string.complete_account_gender_man
}