package com.zrcoding.skincare.ui.home.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.product.ProductModel
import com.zrcoding.skincare.ui.product.fakeProductList
import com.zrcoding.skincare.ui.theme.*

@Composable
fun Favorites() {
    val products = remember { mutableStateOf(fakeProductList) }
    Column(
        Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 21.dp),
            content = {
                items(products.value) {
                    FavoritesItem(
                        productModel = it,
                        onAddToCart = {},
                        onDelete = {}
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun FavoritesPreview() {
    SkincareTheme(darkTheme = false) {
        Favorites()
    }
}

@Composable
fun FavoritesItem(
    productModel: ProductModel,
    modifier: Modifier = Modifier,
    onAddToCart: (ProductModel) -> Unit,
    onDelete: (ProductModel) -> Unit
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
                painter = painterResource(id = productModel.image),
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
                    text = productModel.description,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = productModel.name,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                )
                Spacer(modifier = Modifier.height(4.dp))
                LeftRightComponent(
                    leftComposable = {
                        TextButton(onClick = { onAddToCart(productModel) }) {
                            Text(
                                text = stringResource(id = R.string.favorites_add_to_cart),
                                color = Brown,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    },
                    rightComposable = {
                        Button(
                            onClick = { onDelete(productModel) },
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
            fakeProductList[0],
            onAddToCart = {},
            onDelete = {}
        )
    }
}