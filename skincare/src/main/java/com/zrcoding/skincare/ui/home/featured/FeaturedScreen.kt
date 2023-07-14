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
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.Grey30
import com.zrcoding.skincare.theme.SkincareTheme
import com.zrcoding.skincare.theme.Typography
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.HorizontalProduct
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.SearchView
import com.zrcoding.skincare.ui.components.VerticalProduct

@Composable
fun FeaturedScreen(
    viewModel: FeaturedScreenViewModel = hiltViewModel(),
    onNavigateToProduct: (String) -> Unit,
    onNavigateToExplore: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 21.dp, vertical = 10.dp)
    ) {
        SearchView(
            searchText = viewState.value.searchText,
            placeholder = stringResource(id = R.string.featured_search_placeholder),
            onValueChanged = {
                viewModel.onSearchStarted(it)
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
                    modifier = Modifier.clickable { onNavigateToExplore() }
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipGroup(
            filters = viewState.value.filters,
            selectedFilter = viewState.value.selectedFilter
        ) {
            viewModel.onFilterChanged(it)
        }
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
            items(viewModel.viewState.value.products, key = { it.uuid }) {
                VerticalProduct(
                    product = it,
                    onFavoriteClicked = viewModel::onAddToFavorites,
                    onAddToCartClicked = { product ->
                        onNavigateToProduct(product.uuid)
                    }
                )
            }
        }
        viewState.value.newestProduct?.let {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.featured_new_product),
                color = Brown,
                style = Typography.subtitle1
            )
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalProduct(
                product = it,
                onFavoriteClicked = viewModel::onAddToFavorites
            )
        }
    }
}


@Preview
@Composable
fun FeaturedScreenPreview() {
    SkincareTheme(darkTheme = false) {
        FeaturedScreen(onNavigateToProduct = {}, onNavigateToExplore = {})
    }
}