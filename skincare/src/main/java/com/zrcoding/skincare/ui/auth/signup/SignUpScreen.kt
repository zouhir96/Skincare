package com.zrcoding.skincare.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.AuthBottomQuestionAndClickableText
import com.zrcoding.skincare.ui.components.AuthScreenSubtitle
import com.zrcoding.skincare.ui.components.AuthScreenTitle
import com.zrcoding.skincare.ui.components.ProcessingOperationAnimation
import com.zrcoding.skincare.ui.components.ScPremiumButton
import com.zrcoding.skincare.ui.components.ScTextField
import com.zrcoding.skincare.ui.theme.SkincareTheme

@Composable
fun SignUpRoute(
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToCompleteInfo: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState().value
    SignUpScreen(
        viewState = viewState,
        onNavigateToSignIn = onNavigateToSignIn,
        onEmailTyped = viewModel::onEmailTyped,
        onPasswordTyped = viewModel::onPasswordTyped,
        onConfirmPasswordTyped = viewModel::onConfirmPasswordTyped,
        onSubmit = viewModel::onSubmit
    )
}

@Composable
fun SignUpScreen(
    viewState: SignUpScreenViewState,
    onNavigateToSignIn: () -> Unit,
    onEmailTyped: (String) -> Unit,
    onPasswordTyped: (String) -> Unit,
    onConfirmPasswordTyped: (String) -> Unit,
    onSubmit: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(top = 100.dp, bottom = 40.dp, start = 21.dp, end = 21.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthScreenTitle(
                modifier = Modifier,
                title = R.string.sign_up_title
            )
            Spacer(modifier = Modifier.height(14.dp))
            AuthScreenSubtitle(title = R.string.sign_up_subtitle)
            Spacer(modifier = Modifier.height(45.dp))
            ScTextField(
                enabled = viewState.isProcessing.not(),
                placeholder = R.string.common_email_placeholder,
                text = viewState.email,
                onValueChanged = onEmailTyped,
                error = viewState.emailError,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScTextField(
                enabled = viewState.isProcessing.not(),
                placeholder = R.string.common_password_placeholder,
                text = viewState.password,
                onValueChanged = onPasswordTyped,
                error = viewState.passwordError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScTextField(
                enabled = viewState.isProcessing.not(),
                placeholder = R.string.common_password_placeholder,
                text = viewState.confirmPassword,
                onValueChanged = onConfirmPasswordTyped,
                error = viewState.confirmPasswordError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScPremiumButton(
                enabled = viewState.isProcessing.not(),
                text = R.string.sign_up_next,
                onClick = onSubmit
            )
        }

        AuthBottomQuestionAndClickableText(
            modifier = Modifier.align(Alignment.BottomCenter),
            questionText = R.string.sign_up_already_have_account,
            clickableTextText = R.string.sign_up_already_have_account_sign_in,
            onClick = onNavigateToSignIn
        )
    }
    if (viewState.isProcessing) {
        ProcessingOperationAnimation(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SkincareTheme {
        Surface {
            SignUpScreen(
                viewState = SignUpScreenViewState(
                    email = "randy.goff@example.com",
                    emailError = null,
                    password = "mypassword",
                    passwordError = null,
                    confirmPassword = "mypassword",
                    confirmPasswordError = null,
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
                onConfirmPasswordTyped = {}
            ) {

            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenRequiredEmailPreview() {
    SkincareTheme {
        Surface {
            SignUpScreen(
                viewState = SignUpScreenViewState(
                    email = "",
                    emailError = R.string.common_required_field,
                    password = "mypassword",
                    passwordError = null,
                    confirmPassword = "mypassword",
                    confirmPasswordError = null,
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
                onConfirmPasswordTyped = {}
            ) {

            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPasswordsDoNotMatchPreview() {
    SkincareTheme {
        Surface {
            SignUpScreen(
                viewState = SignUpScreenViewState(
                    email = stringResource(id = R.string.common_email_placeholder),
                    emailError = null,
                    password = "mypassword12",
                    passwordError = null,
                    confirmPassword = "mypassword34",
                    confirmPasswordError = R.string.sign_up_passwords_do_not_match,
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
                onConfirmPasswordTyped = {}
            ) {

            }
        }
    }
}