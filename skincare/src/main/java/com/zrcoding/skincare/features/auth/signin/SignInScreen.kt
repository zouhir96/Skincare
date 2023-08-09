package com.zrcoding.skincare.features.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.components.AuthBottomQuestionAndClickableText
import com.zrcoding.skincare.common.components.AuthScreenSubtitle
import com.zrcoding.skincare.common.components.AuthScreenTitle
import com.zrcoding.skincare.common.components.ProcessingOperationAnimation
import com.zrcoding.skincare.common.components.ScPremiumButton
import com.zrcoding.skincare.common.components.ScTextField
import com.zrcoding.skincare.theme.SkincareTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInRoute(
    viewModel: SignInScreenViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.isLoggedIn.collectLatest { onNavigateToHome() }
    }

    val viewState = viewModel.viewState.collectAsState()
    SignInScreen(
        viewState = viewState.value,
        onNavigateToSignUp = onNavigateToSignUp,
        onEmailTyped = viewModel::onEmailTyped,
        onPasswordTyped = viewModel::onPasswordTyped,
        onSubmit = viewModel::onSubmit
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewState: SignInScreenViewState,
    onNavigateToSignUp: () -> Unit,
    onEmailTyped: (String) -> Unit,
    onPasswordTyped: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(top = 100.dp, bottom = 40.dp, start = 21.dp, end = 21.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val focusManager = LocalFocusManager.current
            AuthScreenTitle(
                modifier = Modifier,
                title = R.string.sign_in_title
            )
            Spacer(modifier = Modifier.height(14.dp))
            AuthScreenSubtitle(title = R.string.sign_in_subtitle)
            Spacer(modifier = Modifier.height(45.dp))
            ScTextField(
                placeholder = R.string.common_email_placeholder,
                text = viewState.email,
                onValueChanged = onEmailTyped,
                error = viewState.emailError,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScTextField(
                placeholder = R.string.common_password_placeholder,
                text = viewState.password,
                onValueChanged = onPasswordTyped,
                error = viewState.passwordError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScPremiumButton(
                text = R.string.common_log_out,
                onClick = {
                    focusManager.clearFocus()
                    onSubmit()
                }
            )
        }

        AuthBottomQuestionAndClickableText(
            modifier = Modifier.align(Alignment.BottomCenter),
            questionText = R.string.sign_in_no_account,
            clickableTextText = R.string.sign_in_create_one,
            onClick = onNavigateToSignUp
        )
    }
    if (viewState.isProcessing) {
        ProcessingOperationAnimation(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SkincareTheme(darkTheme = false) {
        SignInScreen(
            viewState = SignInScreenViewState(
                email = "zouhir.rajdaoui@rajdaoui.com",
                password = "myPassword"
            ),
            onNavigateToSignUp = {},
            onEmailTyped = {},
            onPasswordTyped = {},
            onSubmit = {}
        )
    }
}

@Preview
@Composable
fun SignInScreenLoadingPreview() {
    SkincareTheme(darkTheme = false) {
        SignInScreen(
            viewState = SignInScreenViewState(
                email = "zouhir.rajdaoui@rajdaoui.com",
                password = "myPassword",
                isProcessing = true
            ),
            onNavigateToSignUp = {},
            onEmailTyped = {},
            onPasswordTyped = {},
            onSubmit = {}
        )
    }
}

@Preview
@Composable
fun SignInScreenErrorPreview() {
    SkincareTheme(darkTheme = false) {
        SignInScreen(
            viewState = SignInScreenViewState(
                email = "zouhir.rajdaoui@rajdaoui.com",
                emailError = R.string.common_required_field,
                password = "myPassword"
            ),
            onNavigateToSignUp = {},
            onEmailTyped = {},
            onPasswordTyped = {},
            onSubmit = {}
        )
    }
}