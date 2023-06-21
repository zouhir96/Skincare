package com.zrcoding.skincare.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.HorizontalSteps
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite30
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.SkincareTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Onboarding(
    onNavigateToHome: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val currentPage = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BrownWhite30)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        HorizontalPager(
            count = 4,
            contentPadding = PaddingValues(horizontal = 12.dp),
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page: Int ->
            Page(page = pages[page], modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(
                modifier = Modifier.background(
                    color = Brown,
                    shape = RoundedCornerShape(50)
                ),
                onClick = {
                    if (currentPage > 0)
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage - 1)
                        }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = BrownWhite80
                )
            }
            HorizontalSteps(index = currentPage)
            IconButton(
                modifier = Modifier.background(
                    color = Brown,
                    shape = RoundedCornerShape(50)
                ),
                onClick = {
                    if (currentPage == 3) {
                        viewModel.onOnboardingCompleted()
                        onNavigateToHome()
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(if (currentPage == 3) 0 else currentPage + 1)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = BrownWhite80
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    SkincareTheme {
        Onboarding({})
    }
}

@Composable
fun Page(page: Page, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
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
        Spacer(modifier = Modifier.height(42.dp))
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable()
fun PagePreview() {
    SkincareTheme {
        Page(page = pages[0])
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