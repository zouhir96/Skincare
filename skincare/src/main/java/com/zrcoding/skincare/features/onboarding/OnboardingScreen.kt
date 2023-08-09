package com.zrcoding.skincare.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zrcoding.skincare.common.components.HorizontalSteps
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.BrownWhite30
import com.zrcoding.skincare.theme.BrownWhite80
import com.zrcoding.skincare.theme.SkincareTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute(
    onNavigateToAuthentication: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collectLatest {
            onNavigateToAuthentication()
        }
    }
    OnboardingScreen(onboardingPages) {
        viewModel.onOnboardingCompleted()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    pages: List<Page>,
    onOnboardingCompleted: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val currentPage = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BrownWhite30)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Modifier.weight(1f)
        PaddingValues(horizontal = 12.dp)
        PagerDefaults.flingBehavior(
            state = pagerState,
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            Page(page = pages[it])
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
                    if (currentPage > 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage - 1)
                        }
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
                    if (currentPage < 3) {
                        scope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                    } else {
                        onOnboardingCompleted()
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

@Preview
@Composable
fun OnboardingScreenPreview() {
    SkincareTheme(darkTheme = false) {
        OnboardingScreen(onboardingPages) {}
    }
}

@Composable
fun Page(page: Page, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 21.dp),
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
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(42.dp))
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PagePreview() {
    SkincareTheme { Page(page = onboardingPages[0]) }
}