package com.zrcoding.skincare.ui.home.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.VerticalProduct
import com.zrcoding.skincare.ui.product.fakeProductList
import com.zrcoding.skincare.ui.theme.SkincareTheme

@Composable
fun Explore(modifier: Modifier = Modifier) {
    val products = remember { mutableStateOf(fakeProductList) }
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp, vertical = 10.dp)
    ) {
        FilterChipGroup(
            filters = listOf(),
            onFilterChanged = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 10.dp),
            content = {
                items(listOf<Product>()) {
                    VerticalProduct(
                        product = it,
                        onFavoriteClicked = {},
                        onAddToCartClicked = {}
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun ExplorePreview() {
    SkincareTheme(darkTheme = false) {
        Explore(modifier = Modifier.background(MaterialTheme.colors.background))
    }
}