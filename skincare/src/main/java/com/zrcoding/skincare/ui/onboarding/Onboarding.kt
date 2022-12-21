package com.zrcoding.skincare.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.HorizontalSteps
import com.zrcoding.skincare.ui.navigation.Screen
import com.zrcoding.skincare.ui.theme.JetpackcomposeTheme
import com.zrcoding.skincare.ui.theme.brouwn
import com.zrcoding.skincare.ui.theme.brouwnWhite30
import com.zrcoding.skincare.ui.theme.brouwnWhite80
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Onboarding(navController: NavHostController) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    HorizontalPager(
        count = 4,
        contentPadding = PaddingValues(horizontal = 12.dp),
        state = pagerState,
        modifier = Modifier.background(color = brouwnWhite30)
    ) { page: Int ->
        Page(
            page = pages[page]
        ) {
            if (pagerState.currentPage == 3) {
                navController.navigate(route = Screen.Home.route)
            }
            scope.launch {
                pagerState.animateScrollToPage(if (page == 3) 0 else page + 1)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    JetpackcomposeTheme {

    }
}

@Composable
fun Page(
    page: Page,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 80.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = page.title),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = page.description),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalSteps(index = page.index)
        Spacer(modifier = Modifier.height(18.dp))
        IconButton(
            modifier = Modifier.background(
                color = brouwn,
                shape = RoundedCornerShape(50)
            ),
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = brouwnWhite80
            )
        }
        Spacer(modifier = Modifier.height(42.dp))
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable()
fun PagePreview() {
    JetpackcomposeTheme {
        Page(
            page = pages[0]
        ) {

        }
    }
}

private val pages = listOf(
    Page(
        title = R.string.onboarding_title_1,
        description = R.string.onboarding_description_1,
        image = R.drawable.skincare_products,
        index = 0
    ),
    Page(
        title = R.string.onboarding_title_2,
        description = R.string.onboarding_description_2,
        image = R.drawable.skincare_products,
        index = 1
    ),
    Page(
        title = R.string.onboarding_title_3,
        description = R.string.onboarding_description_3,
        image = R.drawable.skincare_products,
        index = 2
    ),
    Page(
        title = R.string.onboarding_title_4,
        description = R.string.onboarding_description_4,
        image = R.drawable.skincare_products,
        index = 3
    ),
)

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val index: Int
)