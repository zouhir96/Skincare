package com.zrcoding.skincare.features.connected.home.featured

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.components.FilterChipGroup
import com.zrcoding.skincare.common.components.HorizontalProduct
import com.zrcoding.skincare.common.components.LeftRightComponent
import com.zrcoding.skincare.common.components.SearchView
import com.zrcoding.skincare.common.components.VerticalProduct
import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.common.domain.model.filterAll
import com.zrcoding.skincare.common.domain.model.toFilters
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeCategories
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.Grey30
import com.zrcoding.skincare.theme.SkincareTheme
import com.zrcoding.skincare.theme.Typography


@Composable
fun FeaturedScreenRoute(
    viewModel: FeaturedScreenViewModel = hiltViewModel(),
    onNavigateToProduct: (String) -> Unit,
    onNavigateToExplore: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()
    FeaturedScreen(
        viewState.value,
        onNavigateToProduct = onNavigateToProduct,
        onNavigateToExplore = onNavigateToExplore,
        onSearchStarted = viewModel::onSearchStarted,
        onFilterChanged = viewModel::onFilterChanged,
        onAddToFavorites = viewModel::onAddToFavorites
    )
}

@Composable
fun FeaturedScreen(
    viewState: FeaturedScreenViewState,
    onNavigateToProduct: (String) -> Unit,
    onNavigateToExplore: () -> Unit,
    onSearchStarted: (String) -> Unit,
    onFilterChanged: (Filter) -> Unit,
    onAddToFavorites: (Product) -> Unit,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 21.dp, vertical = 10.dp)
    ) {
        SearchView(
            searchText = viewState.searchText,
            placeholder = stringResource(id = R.string.featured_search_placeholder),
            onValueChanged = { onSearchStarted(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        LeftRightComponent(
            leftComposable = {
                Text(
                    text = stringResource(id = R.string.common_category),
                    color = Brown,
                    style = Typography.subtitle1
                )
            },
            rightComposable = {
                Text(
                    text = stringResource(id = R.string.common_see_all),
                    color = Grey30,
                    style = Typography.subtitle1,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onNavigateToExplore() }
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipGroup(
            filters = viewState.filters,
            selectedFilter = viewState.selectedFilter,
            onFilterChanged = { onFilterChanged(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        LeftRightComponent(
            leftComposable = {
                Text(
                    text = stringResource(id = R.string.featured_popular_skincare),
                    color = Brown,
                    style = Typography.subtitle1
                )
            },
            rightComposable = {
                Text(
                    text = stringResource(id = R.string.common_see_all),
                    color = Grey30,
                    style = Typography.subtitle1,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {
            items(viewState.products, key = { it.uuid }) { item ->
                VerticalProduct(
                    product = item,
                    onFavoriteClicked = { onAddToFavorites(it) },
                    onAddToCartClicked = { product ->
                        onNavigateToProduct(product.uuid)
                    }
                )
            }
        }
        viewState.newestProduct?.let { newestProduct ->
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.featured_new_product),
                color = Brown,
                style = Typography.subtitle1
            )
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalProduct(
                product = newestProduct,
                onFavoriteClicked = { onAddToFavorites(it) }
            )
        }
    }
}


@Preview
@Composable
fun FeaturedScreenPreview() {
    SkincareTheme(darkTheme = false) {
        FeaturedScreen(
            viewState = FeaturedScreenViewState(
                searchText = "",
                products = fakeProducts.take(2),
                filters = fakeCategories.toFilters(),
                selectedFilter = filterAll,
                newestProduct = fakeProducts.first()
            ),
            onNavigateToProduct = {},
            onNavigateToExplore = {},
            onSearchStarted = {},
            onFilterChanged = {},
            onAddToFavorites = {}
        )
    }
}