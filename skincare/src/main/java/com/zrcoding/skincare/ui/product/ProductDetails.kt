package com.zrcoding.skincare.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.FilterChipGroup
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.ScreenHeader
import com.zrcoding.skincare.ui.components.Stars
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.Grey50
import com.zrcoding.skincare.ui.theme.White

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetails(
    id: String?,
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenHeader(
                leftIcon = R.drawable.ic_back_brown20,
                onLeftIconClicked = {
                    onBackClicked()
                },
                rightIcon = R.drawable.ic_favorite_brown20,
                onRightIconClicked = {

                },
            )
        },
        backgroundColor = BrownWhite80
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (images, content, buy) = createRefs()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(images) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(content.top)
                        height = Dimension.fillToConstraints
                    }
            ) {
                val pagerState = rememberPagerState()
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalPager(
                    count = 3,
                    state = pagerState,
                    modifier = Modifier
                        .height(0.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.skincare_products),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
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
                    text = "Sunscreen",
                    style = MaterialTheme.typography.body1,
                    color = Grey50,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Protect Available Exclusively from a Skincare",
                    style = MaterialTheme.typography.h6,
                    color = Brown,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Stars(stars = 5)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(2988 Reviews)",
                        style = MaterialTheme.typography.caption,
                        color = Grey50
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Select Volum",
                    style = MaterialTheme.typography.body1,
                    color = Brown,
                )
                Spacer(modifier = Modifier.height(12.dp))
                FilterChipGroup(
                    filters = listOf("150 ml", "250 ml", "350 ml"),
                    onFilterChanged = {}
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Descriptions",
                    style = MaterialTheme.typography.body1,
                    color = Brown,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
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
                        Text(text = "Price", style = MaterialTheme.typography.body1)
                        Text(text = "100.00$", style = MaterialTheme.typography.h6)
                    }
                },
                rightComposable = {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Brown,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(45.dp)
                    ) {
                        Text(text = "Buy Now", style = MaterialTheme.typography.button)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsPreview() {
    ProductDetails("") {}
}