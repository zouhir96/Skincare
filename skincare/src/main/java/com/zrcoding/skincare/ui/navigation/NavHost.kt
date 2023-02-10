package com.zrcoding.skincare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.zrcoding.skincare.ui.cart.Cart
import com.zrcoding.skincare.ui.explore.Explore
import com.zrcoding.skincare.ui.favorite.Favorites
import com.zrcoding.skincare.ui.home.Home
import com.zrcoding.skincare.ui.onboarding.Onboarding
import com.zrcoding.skincare.ui.product.ProductDetails
import com.zrcoding.skincare.ui.profile.Profile

@Composable
fun MNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) { Onboarding(navController) }
        composable(route = Screen.Home.route) { Home(navController) }
        composable(route = Screen.Explore.route) { Explore() }
        composable(route = Screen.Favorites.route) { Favorites() }
        composable(route = Screen.Profile.route) { Profile() }
        dialog(
            route = Screen.Product.route,
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true // passing this as parameter isn't mandatory but I added is for clarification purposes
            )
        ) { ProductDetails(navController = navController) }
        composable(route = Screen.Cart.route) { Cart() }
    }
}