package com.zrcoding.skincare.ui.navigation

const val NAV_ID_PARAM = "{id}"

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Product : Screen("product/$NAV_ID_PARAM")
    object Cart : Screen("cart")
}

fun Screen.Product.routeWithId(id: String) = route.replace(NAV_ID_PARAM, id, true)
