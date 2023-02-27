package com.zrcoding.skincare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zrcoding.skincare.ui.cart.Cart
import com.zrcoding.skincare.ui.home.Home
import com.zrcoding.skincare.ui.onboarding.Onboarding
import com.zrcoding.skincare.ui.product.ProductDetails

@Composable
fun MainNavHost(
    globalNavController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = globalNavController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            Onboarding {
                globalNavController.navigate(route = Screen.Home.route)
            }
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
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            ProductDetails(
                id = backStackEntry.arguments?.getString("id"),
                onBackClicked = {
                    globalNavController.popBackStack()
                }
            )
        }
        composable(route = Screen.Cart.route) {
            Cart(
                onBackClicked = {
                    globalNavController.popBackStack()
                }
            )
        }
    }
}