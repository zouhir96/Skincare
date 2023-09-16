package com.zrcoding.skincare.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zrcoding.skincare.features.auth.navigation.AuthScreen
import com.zrcoding.skincare.features.auth.navigation.authNavGraph
import com.zrcoding.skincare.features.connected.cart.CartRoute
import com.zrcoding.skincare.features.connected.editaccount.EditAccountRoute
import com.zrcoding.skincare.features.connected.home.master.HomeRoute
import com.zrcoding.skincare.features.connected.orders.OrdersRoute
import com.zrcoding.skincare.features.connected.product.ProductDetailsRoute
import com.zrcoding.skincare.features.connected.refund.RefundRoute
import com.zrcoding.skincare.features.onboarding.OnboardingRoute

@Composable
fun MainNavHost(
    onboarded: Boolean,
    modifier: Modifier
) {
    val globalNavController = rememberNavController()
    NavHost(
        navController = globalNavController,
        startDestination = if (onboarded) Screen.Home.route else Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingRoute(
                onNavigateToAuthentication = {
                    globalNavController.navigate(route = AuthScreen.NAV_GRAPH_ROUTE) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        authNavGraph(
            navController = globalNavController,
            onNavigateToHome = {
                globalNavController.navigate(route = Screen.Home.route) {
                    popUpTo(AuthScreen.NAV_GRAPH_ROUTE) { inclusive = true }
                }
            }
        )
        composable(route = Screen.Home.route) {
            HomeRoute(
                onNavigateToProduct = {
                    globalNavController.navigate(Screen.Product.routeWithId(it))
                },
                onNavigateToCart = {
                    globalNavController.navigate(Screen.Cart.route)
                },
                onNavigateToRoute = {
                    if (it.isNotBlank()) {
                        globalNavController.navigate(it)
                    }
                }
            )
        }
        composable(
            route = Screen.Product.route,
            arguments = listOf(navArgument("uuid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uuid = backStackEntry.arguments?.getString("uuid") ?: return@composable
            ProductDetailsRoute(
                uuid = uuid,
                onBackClicked = {
                    globalNavController.popBackStack()
                }
            )
        }
        composable(route = Screen.Cart.route) {
            CartRoute { globalNavController.popBackStack() }
        }
        composable(route = Screen.EditAccount.route) {
            EditAccountRoute()
        }
        composable(route = Screen.Orders.route) {
            OrdersRoute()
        }
        composable(route = Screen.Refund.route) {
            RefundRoute()
        }

    }
}