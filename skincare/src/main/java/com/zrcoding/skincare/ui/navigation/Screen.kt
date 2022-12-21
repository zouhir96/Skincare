package com.zrcoding.skincare.ui.navigation

import androidx.annotation.StringRes
import com.zrcoding.skincare.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Onboarding : Screen("onboarding", 0)
    object Home : Screen("home", R.string.home)
    object Explore : Screen("explore", R.string.explore)
    object Favorites : Screen("favorites", R.string.favorites)
    object Profile : Screen("profile", R.string.profile)
    object Product : Screen("product", 0)
    object Cart : Screen("cart", 0)
}

val navigationBarScreens = listOf(
    Screen.Home,
    Screen.Explore,
    Screen.Favorites,
    Screen.Profile,
)
