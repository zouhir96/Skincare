package com.zrcoding.skincare.ui.home

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.*
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.Grey30
import com.zrcoding.skincare.ui.theme.JetpackcomposeTheme
import com.zrcoding.skincare.ui.theme.Typography

@Composable
fun Home() {
    val searchText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp, vertical = 10.dp)
            .background(MaterialTheme.colors.background)
    ) {
        SearchView(
            searchText = searchText.value,
            onValueChanged = {
                searchText.value = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        LeftRightComponent(
            leftComposable = {
                Text(
                    text = "Category",
                    color = Brown,
                    style = Typography.subtitle1
                )
            },
            rightComposable = {
                Text(
                    text = "See all",
                    color = Grey30,
                    style = Typography.subtitle1,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilterChipGroup(filters = listOf("All", "Cleanser", "Toner", "Serum", "Handbody")) {

        }
        Spacer(modifier = Modifier.height(24.dp))
        LeftRightComponent(
            leftComposable = {
                Text(
                    text = "Popular Skincare",
                    color = Brown,
                    style = Typography.subtitle1
                )
            },
            rightComposable = {
                Text(
                    text = "See all",
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
            items(
                listOf(
                    com.zrcoding.skincare.ui.product.Product(
                        name = "Toner",
                        description = "Circumference Active Botanical Refining Toner",
                        price = 60.00,
                        image = R.drawable.skincare_products,
                        stars = 4
                    ),
                    com.zrcoding.skincare.ui.product.Product(
                        name = "Toner",
                        description = "Circumference Active Botanical Refining Toner",
                        price = 60.00,
                        image = R.drawable.skincare_products,
                        stars = 4
                    )
                )
            ) {
                Product(
                    product = it,
                    onFavoriteClicked = {},
                    onAddToCartClicked = {}
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "New Product",
            color = Brown,
            style = Typography.subtitle1
        )
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalProduct(
            product = com.zrcoding.skincare.ui.product.Product(
                name = "Toner",
                description = "Circumference Active Botanical Refining Toner",
                price = 60.00,
                image = R.drawable.skincare_products,
                stars = 4
            ), onFavoriteClicked = {

            }
        )
    }
}


@Preview
@Composable
fun HomePreview() {
    JetpackcomposeTheme(darkTheme = false) {
        Home()
    }
}