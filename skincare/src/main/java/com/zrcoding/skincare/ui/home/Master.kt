package com.zrcoding.skincare.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.home.navigation.BottomBarItem
import com.zrcoding.skincare.ui.home.navigation.HomeNavHost
import com.zrcoding.skincare.ui.home.navigation.bottomNavigationBarScreens
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.Brown80
import com.zrcoding.skincare.ui.theme.CoralReef
import com.zrcoding.skincare.ui.theme.Grey
import com.zrcoding.skincare.ui.theme.Lotion
import com.zrcoding.skincare.ui.theme.White

@Composable
fun Home(
    onNavigateToProduct: (String) -> Unit,
    onNavigateToCart: () -> Unit,
) {
    val homeNavController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(onNavigateToCart = { onNavigateToCart() })
        },
        bottomBar = {
            BottomNavigationBar(
                navController = homeNavController,
                navigationBarScreens = bottomNavigationBarScreens,
            )
        }
    ) { innerPadding ->
        HomeNavHost(
            homeNavController = homeNavController,
            onNavigateToProduct = onNavigateToProduct,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TopAppBar(
    cartItemsCount: Int = 0,
    onNavigateToCart: () -> Unit,
) {
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Public", style = MaterialTheme.typography.subtitle2)
                Text(text = "Skincare", style = MaterialTheme.typography.body1)
            }
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        actions = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Lotion, shape = MaterialTheme.shapes.small)
                    .clickable { onNavigateToCart() }
            ) {
                BadgedBox(
                    badge = {
                        Badge(
                            backgroundColor = CoralReef,
                            contentColor = Color.White
                        ) {
                            Text(text = "$cartItemsCount")
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bag),
                        contentDescription = null,
                        tint = Brown,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigationBarScreens: List<BottomBarItem>,
) {
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