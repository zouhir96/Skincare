package com.zrcoding.skincare.ui.navigation

const val NAV_UUID_PARAM = "{uuid}"

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Product : Screen("product/$NAV_UUID_PARAM")
    object Cart : Screen("cart")
}

fun Screen.Product.routeWithId(id: String) = route.replace(NAV_UUID_PARAM, id, true)
