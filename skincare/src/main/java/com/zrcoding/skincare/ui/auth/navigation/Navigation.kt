package com.zrcoding.skincare.ui.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.zrcoding.skincare.ui.auth.completaccount.CompleteAccountRoute
import com.zrcoding.skincare.ui.auth.navigation.AuthScreen.Companion.NAV_GRAPH_ROUTE
import com.zrcoding.skincare.ui.auth.signin.SignInRoute
import com.zrcoding.skincare.ui.auth.signup.SignUpRoute

sealed class AuthScreen(val route: String) {
    companion object {
        const val NAV_GRAPH_ROUTE = "auth"
    }

    object SignIn : AuthScreen("signIn")
    object SignUp : AuthScreen("signUn")
    object CompleteAccount : AuthScreen("completeAccount")
}


fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onNavigateToHome: () -> Unit,
) {
    navigation(startDestination = AuthScreen.SignIn.route, route = NAV_GRAPH_ROUTE) {
        composable(route = AuthScreen.SignIn.route) {
            SignInRoute(
                onNavigateToHome = onNavigateToHome,
                onNavigateToSignUp = { navController.navigate(AuthScreen.SignUp.route) }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpRoute(
                onNavigateToSignIn = {
                    navController.navigate(AuthScreen.SignIn.route)
                },
                onNavigateToCompleteInfo = {
                    navController.navigate(AuthScreen.CompleteAccount.route)
                }
            )
        }
        composable(route = AuthScreen.CompleteAccount.route) {
            CompleteAccountRoute(onNavigateToHome = onNavigateToHome)
        }
    }
}