package com.zrcoding.skincare.ui.home.featured

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.*
import com.zrcoding.skincare.ui.product.fakeProductList
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.Grey30
import com.zrcoding.skincare.ui.theme.SkincareTheme
import com.zrcoding.skincare.ui.theme.Typography

@Composable
fun Featured(onNavigateToProduct: (String) -> Unit) {
    val emptyText = stringResource(id = R.string.common_empty)
    val searchText = remember { mutableStateOf(emptyText) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 21.dp, vertical = 10.dp)
    ) {
        SearchView(
            searchText = searchText.value,
            placeholder = stringResource(id = R.string.featured_search_placeholder),
            onValueChanged = {
                searchText.value = it
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
                    modifier = Modifier.clickable { }
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipGroup(
            filters = listOf("All", "Cleanser", "Toner", "Serum", "Handbody")
        ) {

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
            items(fakeProductList.take(2)) {
                Product(
                    productModel = it,
                    onFavoriteClicked = {},
                    onAddToCartClicked = { product ->
                        onNavigateToProduct(product.name)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.featured_new_product),
            color = Brown,
            style = Typography.subtitle1
        )
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalProduct(
            productModel = fakeProductList[0],
            onFavoriteClicked = {}
        )
    }
}


@Preview
@Composable
fun HomePreview() {
    SkincareTheme(darkTheme = false) {
        Featured(onNavigateToProduct = {})
    }
}