package com.zrcoding.skincare.ui.home.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.Product
import com.zrcoding.skincare.ui.product.fakeProductList
import com.zrcoding.skincare.ui.theme.SkincareTheme

@Composable
fun Explore(globalNavController: NavHostController) {
    val products = remember { mutableStateOf(fakeProductList) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp)
            .padding(top = 10.dp)
    ) {
        FilterChipGroup(
            filters = listOf(
                "All", "Cleanser", "Toner", "Serum", "Handbody"
            ),
            onFilterChanged = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 10.dp),
            content = {
                items(products.value) {
                    Product(it, {}, {})
                }
            },
            modifier = Modifier
                .height(0.dp)
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun ExplorePreview() {
    SkincareTheme(darkTheme = false) {
        Explore(rememberNavController())
    }
}