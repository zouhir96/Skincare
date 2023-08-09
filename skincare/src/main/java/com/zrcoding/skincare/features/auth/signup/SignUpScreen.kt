package com.zrcoding.skincare.features.auth.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.SkincareTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpRoute(
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToCompleteInfo: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.isSignedUp.collectLatest { onNavigateToCompleteInfo() }
    }

    val viewState = viewModel.viewState.collectAsState().value
    SignUpScreen(
        viewState = viewState,
        onNavigateToSignIn = onNavigateToSignIn,
        onEmailTyped = viewModel::onEmailTyped,
        onPasswordTyped = viewModel::onPasswordTyped,
        onSubmit = viewModel::onSubmit
    )
}

@Composable
fun SignUpScreen(
    viewState: SignUpScreenViewState,
    onNavigateToSignIn: () -> Unit,
    onEmailTyped: (String) -> Unit,
    onPasswordTyped: (String) -> Unit,
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
            val focusManager = LocalFocusManager.current
            AuthScreenTitle(
                modifier = Modifier,
                title = R.string.sign_up_title
            )
            Spacer(modifier = Modifier.height(14.dp))
            AuthScreenSubtitle(title = R.string.sign_up_subtitle)
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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordValidationConstraints(
                modifier = Modifier.padding(start = 20.dp),
                constraints = with(viewState.passwordValidationConstraints) {
                    listOf(
                        R.string.sign_up_password_validation_constraint_1 to passwordConstraint8Characters,
                        R.string.sign_up_password_validation_constraint_2 to passwordConstraint1Uppercase,
                        R.string.sign_up_password_validation_constraint_3 to passwordConstraint1Symbol,
                        R.string.sign_up_password_validation_constraint_4 to passwordConstraint1Number,
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScPremiumButton(
                enabled = viewState.canSubmit(),
                text = R.string.sign_up_next,
                onClick = {
                    focusManager.clearFocus()
                    onSubmit()
                }
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
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
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
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
            ) {

            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPasswordNotValidPreview() {
    SkincareTheme {
        Surface {
            SignUpScreen(
                viewState = SignUpScreenViewState(
                    email = stringResource(id = R.string.common_email_placeholder),
                    emailError = null,
                    password = "mypassword12",
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
            ) {

            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPasswordValidPreview() {
    SkincareTheme {
        Surface {
            SignUpScreen(
                viewState = SignUpScreenViewState(
                    email = stringResource(id = R.string.common_email_placeholder),
                    emailError = null,
                    password = "mypassword12",
                    passwordValidationConstraints = PasswordValidationConstraints(
                        passwordConstraint8Characters = true,
                        passwordConstraint1Uppercase = true,
                        passwordConstraint1Symbol = true,
                        passwordConstraint1Number = true
                    ),
                    isProcessing = false
                ),
                onNavigateToSignIn = {},
                onEmailTyped = {},
                onPasswordTyped = {},
            ) {

            }
        }
    }
}

@Composable
fun PasswordValidationConstraints(
    modifier: Modifier = Modifier,
    constraints: List<Pair<Int, Boolean>>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        constraints.forEach {
            PasswordValidationConstraint(
                validated = it.second,
                text = it.first
            )
        }
    }
}

@Preview
@Composable
fun PasswordValidationConstraintsPreview() {
    SkincareTheme {
        Surface {
            PasswordValidationConstraints(
                constraints = listOf(
                    R.string.sign_up_password_validation_constraint_1 to false,
                    R.string.sign_up_password_validation_constraint_2 to false,
                    R.string.sign_up_password_validation_constraint_3 to false,
                    R.string.sign_up_password_validation_constraint_4 to false
                )
            )
        }
    }
}

@Preview
@Composable
fun PasswordValidationConstraintsAllValidPreview() {
    SkincareTheme {
        Surface {
            PasswordValidationConstraints(
                constraints = listOf(
                    R.string.sign_up_password_validation_constraint_1 to true,
                    R.string.sign_up_password_validation_constraint_2 to true,
                    R.string.sign_up_password_validation_constraint_3 to true,
                    R.string.sign_up_password_validation_constraint_4 to true
                )
            )
        }
    }
}

@Composable
fun PasswordValidationConstraint(
    validated: Boolean,
    @StringRes text: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconId = if (validated) R.drawable.ic_checked else R.drawable.ic_unchecked
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = text),
            color = Brown,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
fun PasswordValidationConstraintPreview() {
    SkincareTheme {
        Surface {
            PasswordValidationConstraint(
                validated = false,
                text = R.string.sign_up_password_validation_constraint_1
            )
        }
    }
}

@Preview
@Composable
fun PasswordValidationConstraintValidatedPreview() {
    SkincareTheme {
        Surface {
            PasswordValidationConstraint(
                validated = true,
                text = R.string.sign_up_password_validation_constraint_1
            )
        }
    }
}