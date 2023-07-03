package com.zrcoding.skincare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zrcoding.skincare.ui.cart.CartScreen
import com.zrcoding.skincare.ui.home.master.Home
import com.zrcoding.skincare.ui.onboarding.OnboardingRoute
import com.zrcoding.skincare.ui.product.ProductDetailsScreen

@Composable
fun MainNavHost(
    onboarded: Boolean,
    globalNavController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = globalNavController,
        startDestination = if (onboarded) Screen.Home.route else Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingRoute(
                onNavigateToHome = {
                    globalNavController.navigate(route = Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            Home(
                onNavigateToProduct = {
                    globalNavController.navigate(Screen.Product.routeWithId(it))
                },
                onNavigateToCart = {
                    globalNavController.navigate(Screen.Cart.route)
                }
            )
        }
        composable(
            route = Screen.Product.route,
            arguments = listOf(navArgument("uuid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uuid = backStackEntry.arguments?.getString("uuid") ?: return@composable
            ProductDetailsScreen(
                uuid = uuid,
                onBackClicked = {
                    globalNavController.popBackStack()
                }
            )
        }
        composable(route = Screen.Cart.route) {
            CartScreen(
                onBackClicked = {
                    globalNavController.popBackStack()
                }
            )
        }
    }
}