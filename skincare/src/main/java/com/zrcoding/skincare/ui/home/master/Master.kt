package com.zrcoding.skincare.ui.home.master

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.CoralReef
import com.zrcoding.skincare.ui.theme.Grey
import com.zrcoding.skincare.ui.theme.Lotion
import com.zrcoding.skincare.ui.theme.SkincareTheme
import com.zrcoding.skincare.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    viewModel: MasterViewModel = hiltViewModel(),
    onNavigateToProduct: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
) {
    val cartItemsCount = viewModel.cartItemsCount.collectAsState()

    Home(
        cartItemsCount = cartItemsCount.value,
        onNavigateToProduct = onNavigateToProduct,
        onNavigateToCart = onNavigateToCart,
        onNavigateToRoute = onNavigateToRoute
    )
}

@Composable
fun Home(
    cartItemsCount: Int,
    onNavigateToProduct: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val homeNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                cartItemsCount = cartItemsCount,
                onNavigateIconClicked = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
                onNavigateToCart = onNavigateToCart
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = homeNavController,
                navigationBarScreens = bottomNavigationBarScreens,
            )
        },
        drawerContent = {
            NavigationDrawer {
                scope.launch { scaffoldState.drawerState.close() }
            }
        },
        drawerShape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
    ) { innerPadding ->
        HomeNavHost(
            homeNavController = homeNavController,
            onNavigateToProduct = onNavigateToProduct,
            onNavigateToRoute = onNavigateToRoute,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun HomePreview() {
    SkincareTheme(darkTheme = false) {
        Home(
            cartItemsCount = 10,
            onNavigateToCart = {},
            onNavigateToProduct = {},
            onNavigateToRoute = {}
        )
    }
}

@Composable
fun TopAppBar(
    cartItemsCount: Int = 0,
    onNavigateIconClicked: () -> Unit,
    onNavigateToCart: () -> Unit,
) {
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.toolbar_title_part1),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = stringResource(R.string.toolbar_title_part2),
                    style = MaterialTheme.typography.body1
                )
            }
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onNavigateIconClicked() }
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

@Preview
@Composable
fun TopAppBarPreview() {
    SkincareTheme(darkTheme = false) {
        TopAppBar(cartItemsCount = 10, onNavigateIconClicked = {}, onNavigateToCart = {})
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigationBarScreens: List<BottomBarItem>,
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
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

@Preview
@Composable
fun BottomNavigationBarPreview() {
    SkincareTheme(darkTheme = false) {
        BottomNavigationBar(
            navController = rememberNavController(),
            navigationBarScreens = bottomNavigationBarScreens
        )
    }
}

data class DrawerItem(@DrawableRes val icon: Int, @StringRes val title: Int, val route: String)

val navigationDrawerItems = listOf(
    DrawerItem(icon = R.drawable.ic_user_40, title = R.string.nd_about, route = ""),
    DrawerItem(icon = R.drawable.ic_user_40, title = R.string.nd_rate, route = ""),
    DrawerItem(icon = R.drawable.ic_user_40, title = R.string.nd_share, route = ""),
    DrawerItem(icon = R.drawable.ic_user_40, title = R.string.nd_support, route = ""),
)

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 22.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = Brown,
            style = MaterialTheme.typography.h3
        )
        LazyColumn(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(navigationDrawerItems) { index, item ->
                NavigationDrawerItem(data = item) { onItemClicked(item.route) }
                if (navigationDrawerItems.lastIndex > index) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun NavigationDrawerPreview() {
    SkincareTheme(darkTheme = false) {
        NavigationDrawer(modifier = Modifier.background(White)) {}
    }
}

@Composable
fun NavigationDrawerItem(data: DrawerItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
            .background(color = BrownWhite80)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = data.icon),
            contentDescription = null
        )
        Text(
            text = stringResource(id = data.title),
            color = Brown,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
fun NavigationDrawerItemPreview() {
    SkincareTheme(darkTheme = false) {
        NavigationDrawerItem(
            DrawerItem(
                icon = R.drawable.ic_user_40,
                title = R.string.nd_about,
                route = ""
            )
        ) {}
    }
}