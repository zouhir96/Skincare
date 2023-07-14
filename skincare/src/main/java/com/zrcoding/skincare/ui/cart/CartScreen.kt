package com.zrcoding.skincare.ui.cart

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.CartProduct
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.BrownWhite80
import com.zrcoding.skincare.theme.BrownWhite90
import com.zrcoding.skincare.theme.Grey
import com.zrcoding.skincare.theme.SkincareTheme
import com.zrcoding.skincare.theme.White
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.QuantityCounter
import com.zrcoding.skincare.ui.components.ScreenEmptyState
import com.zrcoding.skincare.ui.components.ScreenHeader

@Composable
fun CartScreen(
    viewModel: CartScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsState()
    Scaffold(
        topBar = {
            ScreenHeader(
                leftIcon = R.drawable.ic_back_lotion,
                onLeftIconClicked = onBackClicked,
                rightIcon = R.drawable.ic_none,
                onRightIconClicked = {},
                title = stringResource(id = R.string.cart_title)
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    ) { padding ->
        when (val state = viewState.value) {
            CartScreenViewState.Loading -> {}

            is CartScreenViewState.Data -> CartScreenContent(
                modifier = Modifier.padding(padding),
                data = state,
                onIncrementProductQuantity = { viewModel.incrementProductQte(it) },
                onDecrementProductQuantity = { viewModel.decrementProductQte(it) },
                onRemoveProduct = { viewModel.onRemoveProduct(it) },
                onPromoCodeTyped = { viewModel.onPromoCodeTyped(it) },
                onApplyPromoCode = { viewModel.applyPromoCode() },
                onCheckout = { viewModel.onCheckoutCart() }
            )

            CartScreenViewState.Empty -> CartScreenEmptyState()

            CartScreenViewState.GotoPayment -> LaunchedEffect(Unit) {
                onBackClicked()
            }
        }
    }
}

@Preview
@Composable
fun CartScreenPreview() {
    SkincareTheme(darkTheme = false) {
        CartScreen(onBackClicked = {})
    }
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    data: CartScreenViewState.Data,
    onIncrementProductQuantity: (CartProduct) -> Unit,
    onDecrementProductQuantity: (CartProduct) -> Unit,
    onRemoveProduct: (CartProduct) -> Unit,
    onPromoCodeTyped: (String) -> Unit,
    onApplyPromoCode: () -> Unit,
    onCheckout: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 21.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(data.products) { item ->
                    CartItem(
                        cartProduct = item,
                        onIncrementProductQuantity = { onIncrementProductQuantity(item) },
                        onDecrementProductQuantity = { onDecrementProductQuantity(item) },
                        onRemoveProduct = { onRemoveProduct(item) }
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        PromoCode(
            value = data.promoCode,
            errorMsg = data.promoCodeError,
            onChanged = { onPromoCodeTyped(it) },
            onApply = { onApplyPromoCode() }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Totals(
            productsCount = data.productsCount,
            subtotal = data.subtotal,
            shipping = data.shipping,
            promoCodeAmount = data.promoCodeAmount,
            totalPayment = data.totalPayment
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = onCheckout,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Brown,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(
                text = stringResource(id = R.string.cart_checkout),
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview
@Composable
fun CartScreenContentPreview() {
    SkincareTheme(darkTheme = false) {
        CartScreenContent(
            modifier = Modifier.background(White),
            data = CartScreenViewState.Data(
                products = fakeProducts
                    .take(3)
                    .mapIndexed { index, product ->
                        CartProduct(
                            product = product,
                            quantity = index
                        )
                    },
                promoCode = "AAABBB",
                promoCodeError = null,
                productsCount = 8,
                subtotal = 100.0,
                shipping = 10.0,
                promoCodeAmount = 8.0,
                totalPayment = 200.0
            ),
            onIncrementProductQuantity = {},
            onDecrementProductQuantity = {},
            onRemoveProduct = {},
            onPromoCodeTyped = {},
            onApplyPromoCode = {},
            onCheckout = {}
        )
    }
}

@Composable
fun CartScreenEmptyState() {
    ScreenEmptyState(
        modifier = Modifier.background(White),
        title = R.string.cart_empty_shopping_cart_title,
        description = R.string.cart_empty_shopping_cart_subtitle,
        image = R.drawable.img_empty_cart
    )
}

@Preview
@Composable
fun CartScreenEmptyStatePreview() {
    SkincareTheme(darkTheme = false) {
        CartScreenEmptyState()
    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    cartProduct: CartProduct,
    onIncrementProductQuantity: () -> Unit,
    onDecrementProductQuantity: () -> Unit,
    onRemoveProduct: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (image, content, quantity, delete) = createRefs()
            AsyncImage(
                model = cartProduct.product.imagesUrls.firstOrNull(),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(BrownWhite80)
                    .padding(8.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        height = Dimension.wrapContent
                    }
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                        end.linkTo(quantity.start)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = cartProduct.product.title,
                    style = MaterialTheme.typography.body2,
                    color = Brown,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = cartProduct.product.category.name,
                    style = MaterialTheme.typography.caption,
                    color = Grey,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(
                        id = R.string.common_price_with_dollar,
                        cartProduct.product.price
                    ),
                    color = Brown,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            QuantityCounter(
                quantity = cartProduct.quantity,
                stock = cartProduct.product.stock,
                onMinusClicked = onDecrementProductQuantity,
                onPlusClicked = onIncrementProductQuantity,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 4.dp)
                    .constrainAs(quantity) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 4.dp, end = 8.dp)
                    .clickable { onRemoveProduct() }
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Preview
@Composable
fun CartItemPreview() {
    SkincareTheme(darkTheme = false) {
        CartItem(
            cartProduct = CartProduct(
                product = fakeProducts[0],
                quantity = 10,
                volume = fakeProducts[0].volumes[0]
            ),
            onIncrementProductQuantity = {},
            onDecrementProductQuantity = {},
            onRemoveProduct = {}
        )
    }
}

@Composable
fun PromoCode(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes errorMsg: Int? = null,
    onChanged: (String) -> Unit,
    onApply: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, BrownWhite90),
                shape = MaterialTheme.shapes.large
            )
            .padding(end = 12.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = { onChanged(it) },
            modifier = Modifier.weight(1f),
            label = {
                errorMsg?.let { Text(text = stringResource(id = errorMsg)) }
            },
            placeholder = { Text(text = stringResource(id = R.string.cart_promo_code_placeholder)) },
            isError = errorMsg != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                textColor = Brown,
                placeholderColor = BrownWhite90,
                cursorColor = Brown,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                leadingIconColor = BrownWhite90,
                trailingIconColor = BrownWhite90,
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                keyboardType = KeyboardType.Text
            )
        )
        Button(
            onClick = {
                onApply()
                if (value.isNotBlank()) {
                    focusManager.clearFocus()
                } else {
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Brown,
                contentColor = Color.White
            ),
            modifier = Modifier.height(34.dp)
        ) {
            Text(
                text = stringResource(id = R.string.cart_promo_code_apply),
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Preview
@Composable
fun PromoCodePreview() {
    SkincareTheme(darkTheme = false) {
        PromoCode(
            modifier = Modifier.background(White),
            value = "AB123IYV",
            onChanged = {},
            onApply = {}
        )
    }
}

@Composable
fun Totals(
    modifier: Modifier = Modifier,
    productsCount: Int,
    subtotal: Double,
    shipping: Double,
    promoCodeAmount: Double,
    totalPayment: Double
) {
    val totals = listOf(
        Pair(
            stringResource(id = R.string.cart_items_label),
            stringResource(id = R.string.cart_items_count, productsCount)
        ),
        Pair(
            stringResource(id = R.string.cart_subtotal_label),
            stringResource(id = R.string.common_price_with_dollar, subtotal)
        ),
        Pair(
            stringResource(id = R.string.cart_shipping_label),
            stringResource(id = R.string.common_price_with_dollar, shipping)
        ),
        Pair(
            stringResource(id = R.string.cart_promo_label),
            stringResource(id = R.string.common_price_with_dollar, promoCodeAmount)
        ),
        Pair(
            stringResource(id = R.string.cart_total_payment_label),
            stringResource(id = R.string.common_price_with_dollar, totalPayment)
        ),
    )
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, BrownWhite90),
                shape = MaterialTheme.shapes.large
            )
            .padding(30.dp)
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = totals,
            contentType = { _, _ -> },
            itemContent = { index, total ->
                Total(total = total)
                if (totals.lastIndex != index) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DashedDivider(thickness = 1.dp, color = BrownWhite90)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
}

@Composable
private fun Total(
    modifier: Modifier = Modifier,
    total: Pair<String, String>
) {
    LeftRightComponent(
        modifier = modifier,
        leftComposable = {
            Text(
                text = total.first,
                color = Brown,
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.W600
            )
        },
        rightComposable = {
            Text(
                text = total.second,
                color = Brown,
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.W600
            )
        }
    )
}

@Preview
@Composable
fun TotalPreview() {
    SkincareTheme(darkTheme = false) {
        Total(
            modifier = Modifier.background(White),
            total = Pair("subtotal", "100$")
        )
    }
}

@Preview
@Composable
fun TotalsPreview() {
    SkincareTheme(darkTheme = false) {
        Totals(
            modifier = Modifier.background(White),
            productsCount = 3,
            subtotal = 100.0,
            shipping = 0.0,
            promoCodeAmount = 10.0,
            totalPayment = 90.0
        )
    }
}

@Preview
@Composable
private fun DashedDividerPreview() {
    DashedDivider(
        color = BrownWhite90,
        thickness = 2.dp,
        modifier = Modifier
            .background(White)
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun DashedDivider(
    thickness: Dp,
    color: Color = MaterialTheme.colors.onSurface,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.fillMaxWidth()
    ) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color,
            style = Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = intervals,
                    phase = phase
                )
            )
        )
    }
}

