package com.zrcoding.skincare.ui.home.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.ui.components.SplitLayout
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.Grey
import com.zrcoding.skincare.ui.theme.Red
import com.zrcoding.skincare.ui.theme.Red80
import com.zrcoding.skincare.ui.theme.SkincareTheme
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    onNavigateToProduct: (String) -> Unit,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val viewState = viewModel.viewState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        when (val state = viewState.value) {
            FavoritesScreenViewState.Loading -> {
                // Not yet implemented}
            }

            FavoritesScreenViewState.EmptyWishList -> EmptyWishList(
                modifier = Modifier.padding(padding)
            )

            is FavoritesScreenViewState.WishList -> WishList(
                favorites = state.wishList,
                onAddToCart = { onNavigateToProduct(it.uuid) },
                onDelete = { scope.launch { viewModel.onDeleteProduct(it.uuid) } },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    SkincareTheme(darkTheme = false) {
        FavoritesScreen(onNavigateToProduct = {})
    }
}

@Composable
fun WishList(
    favorites: List<Product>,
    onAddToCart: (Product) -> Unit,
    onDelete: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 21.dp),
        content = {
            items(items = favorites, key = { it.uuid }) {
                FavoritesItem(
                    product = it,
                    onAddToCart = onAddToCart,
                    onDelete = onDelete
                )
            }
        }
    )
}

@Preview
@Composable
fun WishListPreview() {
    SkincareTheme(darkTheme = false) {
        WishList(favorites = fakeProducts, onAddToCart = {}, onDelete = {})
    }
}

@Composable
fun EmptyWishList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_empty_wishlist),
            modifier = Modifier.size(225.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.favorites_empty_wishlist_title),
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = stringResource(id = R.string.favorites_empty_wishlist_subtitle),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun EmptyWishListPreview() {
    SkincareTheme(darkTheme = false) {
        EmptyWishList(modifier = Modifier.background(color = Color.White))
    }
}

@Composable
fun FavoritesItem(
    product: Product,
    modifier: Modifier = Modifier,
    onAddToCart: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = BrownWhite80,
        elevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, content) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.skincare_products),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .constrainAs(image) {
                        height = Dimension.fillToConstraints
                        top.linkTo(parent.top)
                        bottom.linkTo(content.top)
                    }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background, MaterialTheme.shapes.large)
                    .padding(12.dp)
                    .constrainAs(content) {
                        height = Dimension.wrapContent
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.category.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                )
                Spacer(modifier = Modifier.height(4.dp))
                SplitLayout(
                    startComposable = {
                        TextButton(onClick = { onAddToCart(product) }) {
                            Text(
                                text = stringResource(id = R.string.favorites_add_to_cart),
                                color = Brown,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    },
                    endComposable = {
                        Button(
                            onClick = { onDelete(product) },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Red80,
                                contentColor = Red
                            ),
                            elevation = ButtonDefaults.elevation(
                                0.dp
                            ),
                            shape = CircleShape,
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_trash),
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = stringResource(id = R.string.common_delete),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoritesItemPreview() {
    SkincareTheme(darkTheme = false) {
        FavoritesItem(
            fakeProducts[0],
            onAddToCart = {},
            onDelete = {}
        )
    }
}