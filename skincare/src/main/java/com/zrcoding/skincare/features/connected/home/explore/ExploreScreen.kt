package com.zrcoding.skincare.features.connected.home.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.common.components.FilterChipGroup
import com.zrcoding.skincare.common.components.VerticalProduct
import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.theme.SkincareTheme

@Composable
fun ExploreScreen(
    onNavigateToProduct: (String) -> Unit,
    viewModel: ExploreScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val viewState = viewModel.viewState.collectAsState()

    Scaffold { padding ->
        when (val state = viewState.value) {
            ExploreScreenViewState.Loading -> {
                // Not yet implemented.
            }

            is ExploreScreenViewState.ExploreState -> ExploreScreenContent(
                exploreState = state,
                onFilterChanged = viewModel::onFilterChanged,
                onFavoriteClicked = viewModel::onAddToFavorites,
                onAddToCartClicked = { onNavigateToProduct(it.uuid) },
                modifier = modifier.padding(padding)
            )
        }
    }
}

@Preview
@Composable
fun ExploreScreenPreview() {
    SkincareTheme(darkTheme = false) {
        ExploreScreen(
            onNavigateToProduct = {},
            modifier = Modifier.background(MaterialTheme.colors.background)
        )
    }
}

@Composable
fun ExploreScreenContent(
    exploreState: ExploreScreenViewState.ExploreState,
    onFilterChanged: (Filter) -> Unit,
    onFavoriteClicked: (Product) -> Unit,
    onAddToCartClicked: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        FilterChipGroup(
            filters = exploreState.filters,
            selectedFilter = exploreState.selectedFilter,
            modifier = Modifier.padding(horizontal = 21.dp),
            onFilterChanged = onFilterChanged
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 21.dp, end = 21.dp, bottom = 10.dp),
            content = {
                items(exploreState.products) {
                    VerticalProduct(
                        product = it,
                        onFavoriteClicked = onFavoriteClicked,
                        onAddToCartClicked = onAddToCartClicked
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun ExploreScreenContentPreview() {
    SkincareTheme(darkTheme = false) {
        ExploreScreenContent(
            exploreState = ExploreScreenViewState.ExploreState(
                filters = listOf(
                    Filter("", "filter 1"),
                    Filter("id", "filter 2"),
                    Filter("", "filter 3"),
                    Filter("", "filter 4"),
                    Filter("", "filter 5"),
                ),
                selectedFilter = Filter("id", "filter 2"),
                products = fakeProducts
            ),
            onFilterChanged = {},
            onAddToCartClicked = {},
            onFavoriteClicked = {}
        )
    }
}