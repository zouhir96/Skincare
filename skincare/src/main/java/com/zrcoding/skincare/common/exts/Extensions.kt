package com.zrcoding.skincare.common.exts

import androidx.annotation.StringRes
import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.GENDER

@StringRes
fun GENDER.localizedText(): Int = when (this) {
    GENDER.MALE -> R.string.common_gender_male_abbreviation
    GENDER.FEMALE -> R.string.common_gender_female_abbreviation
}