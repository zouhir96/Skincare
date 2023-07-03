package com.zrcoding.skincare.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zrcoding.skincare.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val index: Int
)

val onboardingPages = listOf(
    Page(
        title = R.string.onboarding_title_1,
        description = R.string.onboarding_description_1,
        image = R.drawable.img_onboarding_first_page,
        index = 0
    ),
    Page(
        title = R.string.onboarding_title_2,
        description = R.string.onboarding_description_2,
        image = R.drawable.img_onboarding_second_page,
        index = 1
    ),
    Page(
        title = R.string.onboarding_title_3,
        description = R.string.onboarding_description_3,
        image = R.drawable.img_onboarding_third_page,
        index = 2
    ),
    Page(
        title = R.string.onboarding_title_4,
        description = R.string.onboarding_description_4,
        image = R.drawable.img_onboarding_fourth_page,
        index = 3
    ),
)