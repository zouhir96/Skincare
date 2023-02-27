package com.zrcoding.skincare.ui.cart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.QuantityCounter
import com.zrcoding.skincare.ui.components.ScreenHeader
import com.zrcoding.skincare.ui.product.ProductModel
import com.zrcoding.skincare.ui.product.fakeProductList
import com.zrcoding.skincare.ui.theme.*

@Composable
fun Cart(
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenHeader(
                leftIcon = R.drawable.ic_back_lotion,
                onLeftIconClicked = { onBackClicked() },
                rightIcon = R.drawable.ic_none,
                onRightIconClicked = { /*TODO*/ },
                title = "Product Cart"
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    ) {
        val promoCode = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 21.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(fakeProductList) { item ->
                        CartItem(productModel = item)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            PromoCode(
                value = promoCode.value,
                onChanged = { code -> promoCode.value = code },
                onApply = {}
            )
            Spacer(modifier = Modifier.height(30.dp))
            Totals()
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Brown,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                Text(text = "Checkout", style = MaterialTheme.typography.button)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview
@Composable
fun CartPreview() {
    SkincareTheme(darkTheme = false) {
        Cart(onBackClicked = {})
    }
}

@Composable
fun CartItem(
    productModel: ProductModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (image, content, quantity) = createRefs()
            Image(
                painter = painterResource(id = productModel.image),
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
            QuantityCounter(
                quantity = 0,
                stock = 10,
                onMinusClicked = { /*TODO*/ },
                onPlusClicked = { /*TODO*/ },
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 4.dp)
                    .constrainAs(quantity) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
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
            productModel = ProductModel(
                name = "Toner",
                description = "Circumference Active Botanical Refining Toner",
                price = 60.00,
                image = R.drawable.skincare_products,
                stars = 4
            )
        )
    }
}

@Composable
fun PromoCode(
    modifier: Modifier = Modifier,
    value: String,
    onChanged: (String) -> Unit,
    onApply: () -> Unit
) {
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
            placeholder = { Text(text = "Promo Code...") },
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
            onClick = { onApply() },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Brown,
                contentColor = Color.White
            ),
            modifier = Modifier.height(34.dp)
        ) {
            Text(
                text = "Apply",
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
fun Totals(modifier: Modifier = Modifier) {
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
        val totals = listOf(
            Pair("Items", "3 Product"),
            Pair("Subtotal", "260.000$"),
            Pair("Shipping", "1.00$"),
            Pair("Promo", "none"),
            Pair("Total Payment", "261.000$"),
        )
        itemsIndexed(
            items = totals,
            contentType = { _, _ -> },
            itemContent = { index, total ->
                LeftRightComponent(
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
                if (totals.lastIndex != index) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DashedDivider(thickness = 1.dp, color = BrownWhite90)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
}

@Preview
@Composable
fun TotalsPreview() {
    SkincareTheme(darkTheme = false) {
        Totals(modifier = Modifier.background(White))
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

