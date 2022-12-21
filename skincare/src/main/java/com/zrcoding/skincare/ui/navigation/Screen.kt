package com.zrcoding.skincare.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zrcoding.skincare.R

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Product : Screen("product")
    object Cart : Screen("cart")
}

data class BottomBarItem(
    val route: String,
    @StringRes val nameResId: Int,
    @DrawableRes val iconResId: Int
)

val navigationBarScreens = listOf(
    BottomBarItem("home", R.string.home, R.drawable.ic_home),
    BottomBarItem("explore", R.string.explore, R.drawable.ic_grid_view),
    BottomBarItem("favorites", R.string.favorites, R.drawable.ic_favorite),
    BottomBarItem("profile", R.string.profile, R.drawable.ic_user),
)
