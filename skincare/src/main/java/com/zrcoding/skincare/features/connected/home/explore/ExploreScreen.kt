package com.zrcoding.skincare.features.connected.home.explore

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
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.components.FilterChipGroup
import com.zrcoding.skincare.common.components.ScreenEmptyState
import com.zrcoding.skincare.common.components.VerticalProduct
import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.theme.SkincareTheme

@Composable
fun ExploreRoute(
    viewModel: ExploreScreenViewModel = hiltViewModel(),
    onNavigateToProduct: (String) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()
    ExploreScreen(
        viewState.value,
        onFilterChanged = viewModel::onFilterChanged,
        onAddToFavorites = viewModel::onAddToFavorites,
        onNavigateToProduct = onNavigateToProduct,
    )
}

@Composable
fun ExploreScreen(
    viewState: ExploreScreenViewState,
    onFilterChanged: (Filter) -> Unit,
    onAddToFavorites: (Product) -> Unit,
    onNavigateToProduct: (String) -> Unit,
) {
    when (viewState) {
        ExploreScreenViewState.Loading -> {
            // Not yet implemented.
        }

        ExploreScreenViewState.EmptyState -> EmptyExplore()

        is ExploreScreenViewState.ExploreState -> ExploreScreenContent(
            exploreState = viewState,
            onFilterChanged = onFilterChanged,
            onFavoriteClicked = onAddToFavorites,
            onAddToCartClicked = { onNavigateToProduct(it.uuid) },
        )
    }
}


@Preview
@Composable
fun ExploreScreenPreview(
    @PreviewParameter(ExploreScreenUiStatePreviewParameterProvider::class) uiState: ExploreScreenViewState
) {
    SkincareTheme(darkTheme = false) {
        Surface {
            ExploreScreen(
                viewState = uiState,
                onFilterChanged = {},
                onAddToFavorites = {},
                onNavigateToProduct = {}
            )
        }
    }
}

class ExploreScreenUiStatePreviewParameterProvider :
    PreviewParameterProvider<ExploreScreenViewState> {
    override val values = sequenceOf(
        ExploreScreenViewState.Loading,
        ExploreScreenViewState.EmptyState,
        ExploreScreenViewState.ExploreState(
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
    )
}

@Composable
fun ExploreScreenContent(
    exploreState: ExploreScreenViewState.ExploreState,
    onFilterChanged: (Filter) -> Unit,
    onFavoriteClicked: (Product) -> Unit,
    onAddToCartClicked: (Product) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
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

@Composable
fun EmptyExplore(
    modifier: Modifier = Modifier
) {
    ScreenEmptyState(
        modifier = modifier,
        title = R.string.explore_empty_catalog_title,
        description = R.string.explore_empty_catalog_subtitle,
        image = R.drawable.img_empty_wishlist
    )
}
