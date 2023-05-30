package com.zrcoding.skincare.ui.home.featured

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
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.ui.common.Filter
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.HorizontalProduct
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.SearchView
import com.zrcoding.skincare.ui.components.VerticalProduct
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.Grey30
import com.zrcoding.skincare.ui.theme.SkincareTheme
import com.zrcoding.skincare.ui.theme.Typography

@Composable
fun FeaturedScreenRoute(
    viewModel: FeaturedScreenViewModel = hiltViewModel(),
    navigateToProduct: (String) -> Unit,
    navigateToExplore: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    FeaturedScreen(
        state = viewState.value,
        onSearchStarted = viewModel::onSearchStarted,
        onFilterChanged = viewModel::onFilterChanged,
        onAddToFavorites = viewModel::onAddToFavorites,
        navigateToProduct = navigateToProduct,
        navigateToExplore = navigateToExplore,
    )
}

@Composable
fun FeaturedScreen(
    state: FeaturedScreenViewState,
    onSearchStarted: (String) -> Unit,
    onFilterChanged: (Filter) -> Unit,
    onAddToFavorites: (Product) -> Unit,
    navigateToProduct: (String) -> Unit,
    navigateToExplore: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 21.dp, vertical = 10.dp)
    ) {
        SearchView(
            searchText = state.searchText,
            placeholder = stringResource(id = R.string.featured_search_placeholder),
            onValueChanged = {
                onSearchStarted(it)
            }
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
                    modifier = Modifier.clickable { navigateToExplore() }
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipGroup(
            filters = state.filters,
            selectedFilter = state.selectedFilter,
            onFilterChanged = onFilterChanged,

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
            items(state.products, key = { it.uuid }) {
                VerticalProduct(
                    product = it,
                    onFavoriteClicked = { onAddToFavorites(it) },
                    onAddToCartClicked = { product ->
                        navigateToProduct(product.uuid)
                    }
                )
            }
        }
        state.newestProduct?.let {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.featured_new_product),
                color = Brown,
                style = Typography.subtitle1
            )
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalProduct(
                product = it,
                onFavoriteClicked = { onAddToFavorites(it) }
            )
        }
    }
}

@Preview
@Composable
fun FeaturedScreenPreview() {
    SkincareTheme(darkTheme = false) {
        FeaturedScreen(navigateToProduct = {},
            navigateToExplore = {},
            state = FeaturedScreenViewState(
                searchText = "",
                products = listOf(),
                filters = listOf(),
                selectedFilter = Filter(id = "", value = ""),
                newestProduct = null
            ),
            onSearchStarted = {},
            onFilterChanged = {},
            onAddToFavorites = {})
    }
}