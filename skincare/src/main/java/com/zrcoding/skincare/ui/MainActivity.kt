package com.zrcoding.skincare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.cart.Cart
import com.zrcoding.skincare.ui.explore.Explore
import com.zrcoding.skincare.ui.favorite.Favorites
import com.zrcoding.skincare.ui.home.Home
import com.zrcoding.skincare.ui.navigation.BottomBarItem
import com.zrcoding.skincare.ui.navigation.Screen
import com.zrcoding.skincare.ui.navigation.navigationBarScreens
import com.zrcoding.skincare.ui.onboarding.Onboarding
import com.zrcoding.skincare.ui.product.Product
import com.zrcoding.skincare.ui.profile.Profile
import com.zrcoding.skincare.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                // Control TopBar and BottomBar
                val visible = navigationBarScreens.any {
                    it.route == navBackStackEntry?.destination?.route
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(navController = navController, visible = visible)
                        },
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                navigationBarScreens = navigationBarScreens,
                                visible = visible
                            )
                        }
                    ) { innerPadding ->
                        MNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(navController: NavController, visible: Boolean) {
    if (visible) {
        TopAppBar(
            title = {
                Column {
                    Text(text = "Public", style = Typography.subtitle2)
                    Text(text = "Skincare", style = Typography.body1)
                }
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.ic_bag),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            backgroundColor = Color.White,
            elevation = 0.dp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigationBarScreens: List<BottomBarItem>,
    visible: Boolean
) {
    if (visible) {
        BottomNavigation(
            backgroundColor = White,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navigationBarScreens.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = screen.iconResId),
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.nameResId)) },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true,
                    selectedContentColor = Brown80,
                    unselectedContentColor = Grey,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) { Onboarding(navController) }
        composable(route = Screen.Home.route) { Home() }
        composable(route = Screen.Explore.route) { Explore() }
        composable(route = Screen.Favorites.route) { Favorites() }
        composable(route = Screen.Profile.route) { Profile() }
        composable(route = Screen.Product.route) { Product() }
        composable(route = Screen.Cart.route) { Cart() }
    }
}