package com.zrcoding.skincare.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.common.components.HorizontalSteps
import com.zrcoding.skincare.theme.Brown
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
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            Page(page = pages[it])
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IndicatorButton(icon = Icons.AutoMirrored.Filled.ArrowBack) {
                if (currentPage > 0) {
                    scope.launch {
                        pagerState.animateScrollToPage(currentPage - 1)
                    }
                }
            }
            HorizontalSteps(index = currentPage)
            IndicatorButton(icon = Icons.AutoMirrored.Filled.ArrowForward) {
                if (currentPage < 3) {
                    scope.launch { pagerState.animateScrollToPage(currentPage + 1) }
                } else {
                    onOnboardingCompleted()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SkincareTheme(darkTheme = false) {
        OnboardingScreen(onboardingPages) {}
    }
}

@Composable
fun IndicatorButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.background(
            color = Brown,
            shape = RoundedCornerShape(50)
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrownWhite80
        )
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