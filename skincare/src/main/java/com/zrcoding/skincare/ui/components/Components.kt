package com.zrcoding.skincare.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.product.ProductModel
import com.zrcoding.skincare.ui.theme.*

@Composable
fun ScreenHeader(
    @DrawableRes leftIcon: Int,
    onLeftIconClicked: () -> Unit,
    @DrawableRes rightIcon: Int,
    onRightIconClicked: () -> Unit,
    title: String = "",
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { onLeftIconClicked() }
        ) {
            Icon(
                painter = painterResource(id = leftIcon),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
        Text(text = title)
        IconButton(
            onClick = { onRightIconClicked() }
        ) {
            Icon(
                painter = painterResource(id = rightIcon),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        ScreenHeader(
            leftIcon = R.drawable.ic_back_brown20,
            onLeftIconClicked = { },
            rightIcon = R.drawable.ic_favorite_brown20,
            onRightIconClicked = { },
            title = "Screen title",
            modifier = Modifier.background(White)
        )
    }
}

@Composable
fun HorizontalSteps(
    count: Int = 4,
    index: Int = 0,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(count = count) {
            Spacer(
                modifier = Modifier
                    .width(30.dp)
                    .height(3.dp)
                    .background(color = if (it == index) Brown else BrownWhite50)
            )
            if (it != count) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun HorizontalStepsPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        HorizontalSteps(1)
    }
}

@Composable
fun SearchView(
    searchText: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = {
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (searchText.isNotBlank()) {
                IconButton(onClick = { onValueChanged("") }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = Brown,
            placeholderColor = BrownWhite90,
            cursorColor = Brown,
            focusedBorderColor = Brown,
            unfocusedBorderColor = BrownWhite90,
            leadingIconColor = BrownWhite90,
            trailingIconColor = BrownWhite90,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    )
}

@Preview
@Composable
private fun SearchViewPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        SearchView("", {})
    }
}

@Composable
fun LeftRightComponent(
    leftComposable: @Composable () -> Unit,
    rightComposable: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        leftComposable()
        Spacer(modifier = Modifier.weight(1F))
        rightComposable()
    }
}

@Preview
@Composable
private fun LeftRightTextPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        LeftRightComponent(
            leftComposable = {
                Text(text = "Left text")
            },
            rightComposable = {
                Text(text = "Right text")
            },
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChipGroup(
    filters: List<String>,
    selectedFilter: String? = null,
    modifier: Modifier = Modifier,
    onFilterChanged: (String) -> Unit
) {
    val state = remember { mutableStateOf(selectedFilter ?: filters[0]) }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(filters) {
            val selected = state.value == it
            FilterChip(
                selected = selected,
                onClick = {
                    state.value = it
                    onFilterChanged.invoke(it)
                },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    if (selected) 0.dp else 1.dp,
                    SolidColor(if (selected) Brown else BrownWhite90)
                ),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = White,
                    selectedBackgroundColor = Brown,
                    contentColor = if (selected) Color.White else Grey40
                ),
                modifier = Modifier.height(48.dp),
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun FilterChipGroupPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        FilterChipGroup(
            listOf("filter 1", "filter 2", "filter 3", "filter 4", "filter 5", "filter 6"),
            "filter 2",
        ) {}
    }
}

@Composable
fun Product(
    productModel: ProductModel,
    onFavoriteClicked: (ProductModel) -> Unit,
    onAddToCartClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = Color(0xFFFFF3E4),
        elevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.6f)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_product_bottom),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val (favorite, image, description, name, bottomContainer) = createRefs()
                Box(
                    modifier = Modifier
                        .size(27.dp)
                        .clip(CircleShape)
                        .background(BrownWhite50)
                        .constrainAs(favorite) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .clickable { onFavoriteClicked(productModel) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(14.dp)
                    )
                }
                Image(
                    painter = painterResource(id = productModel.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .constrainAs(image) {
                            height = Dimension.fillToConstraints
                            top.linkTo(favorite.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(description.top)
                        }
                )
                Text(
                    text = productModel.description,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                    modifier = Modifier
                        .constrainAs(description) {
                            start.linkTo(parent.start)
                            bottom.linkTo(name.top)
                        }
                        .padding(bottom = 6.dp)
                )
                Text(
                    text = productModel.name,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(parent.start)
                            bottom.linkTo(bottomContainer.top)
                        }
                        .padding(bottom = 4.dp)
                )
                LeftRightComponent(
                    leftComposable = {
                        Column {
                            Stars(stars = productModel.stars)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${productModel.price}$",
                                color = Brown,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }, rightComposable = {
                        Box(
                            modifier = Modifier
                                .size(27.dp)
                                .clip(CircleShape)
                                .background(Brown)
                                .clickable { onAddToCartClicked(productModel) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bag),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(14.dp)
                            )
                        }
                    },
                    modifier = Modifier.constrainAs(bottomContainer) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        Product(
            productModel = ProductModel(
                name = "Toner",
                description = "Circumference Active Botanical Refining Toner",
                price = 60.00,
                image = R.drawable.skincare_products,
                stars = 4
            ),
            onFavoriteClicked = {},
            onAddToCartClicked = {},
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        )
    }
}

@Composable
fun Stars(count: Int = 5, stars: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        for (i in 1..count) {
            Icon(
                painter = painterResource(
                    id = if (i <= stars) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                ),
                contentDescription = null,
                Modifier.size(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun StarsPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        Stars(stars = 3, modifier = Modifier.background(color = MaterialTheme.colors.background))
    }
}

@Composable
fun HorizontalProduct(
    productModel: ProductModel,
    onFavoriteClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val (image, content, favorite) = createRefs()
            Image(
                painter = painterResource(id = productModel.image),
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    height = Dimension.wrapContent
                }
            )
            Column(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                        end.linkTo(favorite.start)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = productModel.description,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = productModel.name,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${productModel.price}$",
                    color = Brown,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(favorite) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clickable { onFavoriteClicked(productModel) }
            )
        }
    }
}

@Preview
@Composable
private fun HorizontalProductPreview() {
    JetpackcomposeTheme(darkTheme = false) {
        HorizontalProduct(
            productModel = ProductModel(
                name = "Toner",
                description = "Circumference Active Botanical Refining Toner",
                price = 60.00,
                image = R.drawable.skincare_products,
                stars = 4
            ),
            onFavoriteClicked = {},
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        )
    }
}