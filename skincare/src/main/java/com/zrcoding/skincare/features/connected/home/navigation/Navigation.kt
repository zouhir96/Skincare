package com.zrcoding.skincare.features.connected.home.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zrcoding.skincare.R
import com.zrcoding.skincare.features.connected.home.explore.ExploreScreen
import com.zrcoding.skincare.features.connected.home.favorite.FavoritesRoute
import com.zrcoding.skincare.features.connected.home.featured.FeaturedScreenRoute
import com.zrcoding.skincare.features.connected.home.profile.ProfileRoute

sealed class HomeScreen(val route: String) {
    data object Featured : HomeScreen("featured")
    data object Explore : HomeScreen("explore")
    data object Favorites : HomeScreen("favorites")
    data object Profile : HomeScreen("profile")
}

data class BottomBarItem(
    val route: String,
    @StringRes val nameResId: Int,
    @DrawableRes val iconResId: Int
)

val bottomNavigationBarScreens = listOf(
    BottomBarItem("featured", R.string.home, R.drawable.ic_home),
    BottomBarItem("explore", R.string.explore, R.drawable.ic_grid_view),
    BottomBarItem("favorites", R.string.favorites, R.drawable.ic_favorite),
    BottomBarItem("profile", R.string.profile, R.drawable.ic_user),
)

@Composable
fun HomeNavHost(
    homeNavController: NavHostController,
    onNavigateToProduct: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = homeNavController,
        startDestination = HomeScreen.Featured.route,
        modifier = modifier
    ) {
        composable(route = HomeScreen.Featured.route) {
            FeaturedScreenRoute(
                onNavigateToProduct = onNavigateToProduct,
            ) {
                homeNavController.navigate(HomeScreen.Explore.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(homeNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        }
        composable(route = HomeScreen.Explore.route) {
            ExploreScreen(onNavigateToProduct = onNavigateToProduct)
        }
        composable(route = HomeScreen.Favorites.route) {
            FavoritesRoute(onNavigateToProduct = onNavigateToProduct)
        }
        composable(route = HomeScreen.Profile.route) {
            ProfileRoute(navigateTo = onNavigateToRoute)
        }
    }
}