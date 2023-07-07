package com.zrcoding.skincare.ui.navigation

const val NAV_UUID_PARAM = "{uuid}"

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Product : Screen("product/$NAV_UUID_PARAM")
    object Cart : Screen("cart")
    object EditAccount : Screen("editAccount")
    object Orders : Screen("orders")
    object Refund : Screen("refund")
}

fun Screen.Product.routeWithId(id: String) = route.replace(NAV_UUID_PARAM, id, true)
