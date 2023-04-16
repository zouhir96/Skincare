package com.zrcoding.skincare.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val index: Int
)