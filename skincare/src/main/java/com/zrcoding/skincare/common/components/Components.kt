package com.zrcoding.skincare.common.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.domain.model.Filter
import com.zrcoding.skincare.data.domain.model.MIN_CART_PRODUCT_QUANTITY
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.BrownWhite50
import com.zrcoding.skincare.theme.BrownWhite90
import com.zrcoding.skincare.theme.Grey
import com.zrcoding.skincare.theme.Grey30
import com.zrcoding.skincare.theme.Grey40
import com.zrcoding.skincare.theme.SkincareTheme
import com.zrcoding.skincare.theme.White
import com.zrcoding.skincare.ui.theme.*

@Composable
fun ScreenHeader(
    @DrawableRes leftIcon: Int,
    onLeftIconClicked: () -> Unit,
    @DrawableRes rightIcon: Int,
    onRightIconClicked: () -> Unit,
    title: String = stringResource(id = R.string.common_empty),
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
    SkincareTheme(darkTheme = false) {
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
    SkincareTheme(darkTheme = false) {
        HorizontalSteps(1)
    }
}

@Composable
fun SearchView(
    searchText: String,
    placeholder: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.W400,
            )
        },
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
    SkincareTheme(darkTheme = false) {
        SearchView(
            searchText = "",
            placeholder = stringResource(id = R.string.featured_search_placeholder),
            onValueChanged = {}
        )
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
    SkincareTheme(darkTheme = false) {
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
    filters: List<Filter>,
    selectedFilter: Filter? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    modifier: Modifier = Modifier,
    onFilterChanged: (Filter) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(filters) {
            val selected = selectedFilter?.id == it.id
            FilterChip(
                selected = selected,
                onClick = {
                    onFilterChanged.invoke(it)
                },
                shape = shape,
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
                    text = it.value,
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
    SkincareTheme(darkTheme = false) {
        FilterChipGroup(
            filters = listOf(
                Filter("1", "filter 1"),
                Filter("2", "filter 2"),
                Filter("3", "filter 3"),
                Filter("4", "filter 4"),
                Filter("5", "filter 5"),
                Filter("6", "filter 6"),
            ),
            selectedFilter = Filter("2", "filter 2")
        ) {}
    }
}

@Composable
fun VerticalProduct(
    product: Product,
    onFavoriteClicked: (Product) -> Unit,
    onAddToCartClicked: (Product) -> Unit,
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
                        .clickable { onFavoriteClicked(product) }
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
                    painter = painterResource(id = R.drawable.skincare_products),
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
                    text = product.title,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                    maxLines = 2,
                    modifier = Modifier
                        .constrainAs(description) {
                            start.linkTo(parent.start)
                            bottom.linkTo(name.top)
                        }
                        .padding(bottom = 6.dp)
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                    maxLines = 2,
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
                            Stars(stars = product.stars)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${product.price}$",
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
                                .clickable { onAddToCartClicked(product) }
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
    SkincareTheme(darkTheme = false) {
        VerticalProduct(
            product = fakeProducts[0],
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
    SkincareTheme(darkTheme = false) {
        Stars(stars = 3, modifier = Modifier.background(color = MaterialTheme.colors.background))
    }
}

@Composable
fun HorizontalProduct(
    product: Product,
    onFavoriteClicked: (Product) -> Unit,
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
                painter = painterResource(id = R.drawable.skincare_products),
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
                    text = product.description,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${product.price}$",
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
                    .clickable { onFavoriteClicked(product) }
            )
        }
    }
}

@Preview
@Composable
private fun HorizontalProductPreview() {
    SkincareTheme(darkTheme = false) {
        HorizontalProduct(
            product = fakeProducts[0],
            onFavoriteClicked = {},
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        )
    }
}

@Composable
fun QuantityCounter(
    quantity: Int,
    stock: Int,
    onMinusClicked: () -> Unit,
    onPlusClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_minus),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .clickable { if (quantity > MIN_CART_PRODUCT_QUANTITY) onMinusClicked() },
            tint = Color.Unspecified
        )

        Text(
            text = "$quantity",
            style = MaterialTheme.typography.body1,
            color = Brown
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .clickable { if (quantity < stock) onPlusClicked() },
            tint = Color.Unspecified
        )

    }
}

@Preview
@Composable
fun QuantityCounterPreview() {
    SkincareTheme(darkTheme = false) {
        QuantityCounter(
            quantity = 2,
            stock = 10,
            onMinusClicked = {},
            onPlusClicked = {},
            modifier = Modifier.background(White)
        )
    }
}

@Composable
fun ScreenEmptyState(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int,
    @DrawableRes image: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = image),
            modifier = Modifier.size(225.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AuthScreenTitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Text(
        text = stringResource(id = title),
        modifier = modifier,
        color = Brown,
        style = MaterialTheme.typography.h4,
        maxLines = 1,
        textAlign = TextAlign.Center
    )
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun AuthScreenTitlePreview() {
    SkincareTheme(darkTheme = false) {
        AuthScreenTitle(
            title = R.string.cart_title,
            modifier = Modifier.background(MaterialTheme.colors.background)
        )
    }
}

@Composable
fun AuthScreenSubtitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Text(
        text = stringResource(id = title),
        modifier = modifier,
        color = BrownWhite50,
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun AuthScreenSubtitlePreview() {
    SkincareTheme(darkTheme = false) {
        AuthScreenSubtitle(
            title = R.string.cart_empty_shopping_cart_subtitle,
            modifier = Modifier.background(MaterialTheme.colors.background)
        )
    }
}

@Composable
fun ScTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @StringRes placeholder: Int,
    text: String,
    onValueChanged: (String) -> Unit,
    error: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChanged,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = enabled,
            placeholder = {
                Text(text = stringResource(id = placeholder))
            },
            isError = error != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = if (error != null) MaterialTheme.colors.error else Brown,
                placeholderColor = BrownWhite50,
                cursorColor = Brown,
                focusedBorderColor = Brown,
                unfocusedBorderColor = BrownWhite50,
                leadingIconColor = BrownWhite50,
                trailingIconColor = BrownWhite50,
            ),
        )
        if (error != null) {
            Text(
                text = stringResource(id = error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun ScTextFieldPreview() {
    SkincareTheme(darkTheme = false) {
        ScTextField(
            modifier = Modifier.background(MaterialTheme.colors.background),
            placeholder = R.string.common_email_placeholder,
            text = "",
            onValueChanged = {}
        )
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun ScTextFieldErrorPreview() {
    SkincareTheme(darkTheme = false) {
        ScTextField(
            modifier = Modifier.background(MaterialTheme.colors.background),
            placeholder = R.string.common_email_placeholder,
            text = stringResource(id = R.string.common_email_placeholder),
            onValueChanged = {},
            error = R.string.common_required_field
        )
    }
}

@Composable
fun ScPremiumButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(45.dp)
            .fillMaxWidth(),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Brown,
            disabledBackgroundColor = BrownWhite90,
            contentColor = Color.White,
            disabledContentColor = Color.Black
        ),
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.button
        )
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun ScPremiumButtonPreview() {
    SkincareTheme(darkTheme = false) {
        ScPremiumButton(
            modifier = Modifier.background(MaterialTheme.colors.background),
            text = R.string.common_log_out,
        ) {}
    }
}

@Composable
fun AuthBottomQuestionAndClickableText(
    modifier: Modifier = Modifier,
    @StringRes questionText: Int,
    @StringRes clickableTextText: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = questionText),
            color = Grey30,
            style = MaterialTheme.typography.body1
        )
        ClickableText(
            text = AnnotatedString(
                text = " ${stringResource(id = clickableTextText)}",
                spanStyle = SpanStyle(color = Brown)
            ),
            style = MaterialTheme.typography.body1,
            onClick = { onClick() }
        )
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun AuthBottomQuestionAndClickableTextPreview() {
    SkincareTheme(darkTheme = false) {
        AuthBottomQuestionAndClickableText(
            modifier = Modifier.background(MaterialTheme.colors.background),
            questionText = R.string.sign_in_no_account,
            clickableTextText = R.string.sign_in_create_one
        ) {}
    }
}

@Composable
fun ProcessingOperationAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.processing))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false) { }
            .background(color = Color.Black.copy(alpha = 0.6F)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = modifier,
            composition = composition,
            progress = { progress },
        )
    }
}

@Preview
@Composable
fun ProcessingOperationAnimationPreview() {
    SkincareTheme { Surface { ProcessingOperationAnimation() } }
}

@Composable
fun ScInputLabel(text: String) {
    Text(
        text = text,
        color = Brown,
        style = MaterialTheme.typography.body1
    )
}

@Preview
@Composable
fun ScInputLabelPreview() {
    SkincareTheme {
        Surface {
            ScInputLabel(text = "Name")
        }
    }
}
