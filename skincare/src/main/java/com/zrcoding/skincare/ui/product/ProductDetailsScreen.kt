package com.zrcoding.skincare.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.zrcoding.skincare.R
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.sources.fake.fakeProducts
import com.zrcoding.skincare.ui.common.toFilter
import com.zrcoding.skincare.ui.common.toFilters
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.QuantityCounter
import com.zrcoding.skincare.ui.components.ScreenHeader
import com.zrcoding.skincare.ui.components.Stars
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite50
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.Grey50
import com.zrcoding.skincare.ui.theme.SkincareTheme
import com.zrcoding.skincare.ui.theme.White
import com.zrcoding.skincare.ui.theme.bottomSheetShape
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsScreenViewModel = hiltViewModel(),
    uuid: String,
    onBackClicked: () -> Unit
) {
    viewModel.getProductDetails(uuid)
    val viewState = viewModel.viewState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    Scaffold(
        topBar = {
            ScreenHeader(
                leftIcon = R.drawable.ic_back_brown20,
                onLeftIconClicked = { onBackClicked() },
                rightIcon = R.drawable.ic_favorite_brown20,
                onRightIconClicked = { viewModel.onAddToFavorites(uuid) },
            )
        },
        backgroundColor = BrownWhite80
    ) { paddingValues ->
        when (val state = viewState.value) {
            ProductDetailsScreenViewState.Loading -> Unit

            is ProductDetailsScreenViewState.Details -> {
                val (selectedVolume, setSelectedVolume) = remember {
                    mutableStateOf(state.product.volumes.firstOrNull()?.uuid.orEmpty())
                }
                val (selectedQuantity, setSelectedQuantity) = remember {
                    mutableStateOf(0)
                }
                ModalBottomSheetLayout(
                    sheetState = bottomSheetState,
                    sheetShape = bottomSheetShape,
                    sheetContent = {
                        AddToCart(
                            product = state.product,
                            selectedVolumeUuid = selectedVolume,
                            onVolumeSelected = setSelectedVolume,
                            onQuantitySelected = setSelectedQuantity,
                            onBuyBtnClicked = {
                                coroutineScope.launch { bottomSheetState.hide() }
                            }
                        )
                    },
                ) {
                    ProductDetailsScreenContent(
                        product = state.product,
                        selectedVolumeUuid = selectedVolume,
                        onBuyBtnClicked = { coroutineScope.launch { bottomSheetState.show() } },
                        onVolumeSelected = setSelectedVolume,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }

            ProductDetailsScreenViewState.Error -> Unit
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    ProductDetailsScreen(uuid = "") {}
}

@Composable
fun ProductDetailsScreenContent(
    product: Product,
    onBuyBtnClicked: () -> Unit,
    onVolumeSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedVolumeUuid: String
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (images, content, buy) = createRefs()

        ProductDetailsScreenContentImages(
            images = product.imagesUrls,
            modifier = Modifier.constrainAs(images) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(content.top)
                height = Dimension.fillToConstraints
            }
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buy.top)
                    height = Dimension.wrapContent
                }
                .padding(start = 21.dp, end = 21.dp, top = 30.dp)
        ) {
            Text(
                text = product.category.name,
                style = MaterialTheme.typography.body1,
                color = Grey50,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.h6,
                color = Brown,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = CenterVertically) {
                Stars(stars = product.stars)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.product_details_product_reviews,
                        product.reviews
                    ),
                    style = MaterialTheme.typography.caption,
                    color = Grey50
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.product_details_select_volume),
                style = MaterialTheme.typography.body1,
                color = Brown,
            )
            Spacer(modifier = Modifier.height(12.dp))
            FilterChipGroup(
                filters = product.volumes.toFilters(),
                selectedFilter = product.volumes.find { it.uuid == selectedVolumeUuid }?.toFilter(),
                shape = MaterialTheme.shapes.large,
                onFilterChanged = { onVolumeSelected(it.id) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.product_details_description_label),
                style = MaterialTheme.typography.body1,
                color = Brown,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.caption,
                color = Grey50
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        LeftRightComponent(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(buy) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.wrapContent
                }
                .shadow(15.dp)
                .background(White)
                .padding(horizontal = 21.dp, vertical = 13.dp),
            leftComposable = {
                Column {
                    Text(
                        text = stringResource(id = R.string.common_product_price_label),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = stringResource(
                            id = R.string.common_price_with_dollar,
                            product.price
                        ),
                        style = MaterialTheme.typography.h6
                    )
                }
            },
            rightComposable = {
                Button(
                    onClick = { onBuyBtnClicked() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Brown,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(45.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.product_details_buy_now),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun ProductDetailsScreenContentPreview() {
    SkincareTheme(darkTheme = false) {
        ProductDetailsScreenContent(
            product = fakeProducts[0],
            onBuyBtnClicked = { },
            onVolumeSelected = {},
            modifier = Modifier.background(Color.White),
            selectedVolumeUuid = fakeProducts[0].volumes[0].uuid
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailsScreenContentImages(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
    ) {
        val pagerState = rememberPagerState()
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .height(0.dp)
                .weight(1f)
        ) {
            AsyncImage(
                model = images[it],
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxHeight()
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun ProductDetailsScreenContentImagesPreview() {
    SkincareTheme(darkTheme = false) {
        ProductDetailsScreenContentImages(
            images = fakeProducts[0].imagesUrls,
            modifier = Modifier.background(Color.White)
        )
    }
}


@Composable
fun AddToCart(
    product: Product,
    selectedVolumeUuid: String,
    onVolumeSelected: (String) -> Unit,
    onQuantitySelected: (Int) -> Unit,
    onBuyBtnClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chosenQuantity = remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
    ) {
        Spacer(
            modifier = modifier
                .align(CenterHorizontally)
                .padding(top = 20.dp)
                .width(90.dp)
                .height(3.dp)
                .background(BrownWhite50)
        )
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.skincare_products),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(70.dp)
                    .background(BrownWhite80, MaterialTheme.shapes.medium)
                    .padding(4.dp)
            )
            Spacer(modifier = modifier.width(18.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.common_product_price_label),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = stringResource(id = R.string.common_price_with_dollar, product.price),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.add_to_cart_stock, product.stock),
                    style = MaterialTheme.typography.caption
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.add_to_cart_volume),
            style = MaterialTheme.typography.body1,
            color = Brown,
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilterChipGroup(
            filters = product.volumes.toFilters(),
            selectedFilter = product.volumes.find { it.uuid == selectedVolumeUuid }?.toFilter(),
            shape = MaterialTheme.shapes.large,
            onFilterChanged = { onVolumeSelected(it.id) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(BrownWhite50)
        )
        Spacer(modifier = Modifier.height(20.dp))
        LeftRightComponent(
            leftComposable = {
                Text(
                    text = stringResource(id = R.string.add_to_cart_quantity),
                    style = MaterialTheme.typography.body1,
                    color = Brown
                )
            },
            rightComposable = {
                QuantityCounter(
                    quantity = chosenQuantity.value,
                    stock = 10,
                    onMinusClicked = {
                        chosenQuantity.value -= 1
                        onQuantitySelected(chosenQuantity.value)
                    },
                    onPlusClicked = {
                        chosenQuantity.value += 1
                        onQuantitySelected(chosenQuantity.value)
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(BrownWhite50)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                onBuyBtnClicked()
            },
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
                text = stringResource(id = R.string.add_to_cart_btn_text),
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview
@Composable
fun AddToCartPreview() {
    SkincareTheme(darkTheme = false) {
        AddToCart(
            product = fakeProducts[0],
            selectedVolumeUuid = fakeProducts[0].volumes[0].uuid,
            onVolumeSelected = { },
            onQuantitySelected = { },
            onBuyBtnClicked = {},
            modifier = Modifier.background(White)
        )
    }
}