package com.zrcoding.skincare.features.auth.completaccount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.common.components.LeftRightComponent
import com.zrcoding.skincare.common.components.ProcessingOperationAnimation
import com.zrcoding.skincare.common.components.ScInputLabel
import com.zrcoding.skincare.common.components.ScPremiumButton
import com.zrcoding.skincare.common.components.ScTextField
import com.zrcoding.skincare.data.domain.model.GENDER
import com.zrcoding.skincare.theme.Brown
import com.zrcoding.skincare.theme.BrownWhite50
import com.zrcoding.skincare.theme.BrownWhite80
import com.zrcoding.skincare.theme.SkincareTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CompleteAccountRoute(
    viewModel: CompleteAccountScreenViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.isCompleted.collectLatest { onNavigateToHome() }
    }

    val viewState = viewModel.viewState.collectAsState()
    CompleteAccountScreen(
        state = viewState.value,
        onNameChanged = viewModel::onNameChanged,
        onAgeChanged = viewModel::onAgeChanged,
        onGenderChanged = viewModel::onGenderChanged,
        onSubmit = viewModel::onSubmit
    )
}

@Composable
fun CompleteAccountScreen(
    state: CompleteAccountScreenViewState,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (Float) -> Unit,
    onGenderChanged: (GENDER) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 30.dp)
            .padding(horizontal = 25.dp)
    ) {
        val focusManager = LocalFocusManager.current

        ScInputLabel(text = stringResource(id = R.string.complete_account_name_label))
        Spacer(modifier = Modifier.height(16.dp))
        ScTextField(
            placeholder = R.string.complete_account_name_placeholder,
            text = state.fullName,
            onValueChanged = onNameChanged,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(30.dp))
        LeftRightComponent(
            leftComposable = {
                ScInputLabel(text = stringResource(id = R.string.complete_account_age_label))
            },
            rightComposable = {
                Text(
                    text = stringResource(
                        id = R.string.complete_account_age_value,
                        state.age
                    ),
                    color = BrownWhite50,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        )
        Slider(
            value = state.age.toFloat(),
            onValueChange = {
                focusManager.clearFocus()
                onAgeChanged(it)
            },
            valueRange = AGE_MIN_VALUE.toFloat()..AGE_MAX_VALUE.toFloat(),
            steps = AGE_MAX_STEPS,
            colors = SliderDefaults.colors(
                thumbColor = Brown,
                activeTickColor = Brown,
                inactiveTickColor = BrownWhite80,
                activeTrackColor = Brown,
                inactiveTrackColor = BrownWhite80
            )
        )

        Spacer(modifier = Modifier.height(30.dp))
        ScInputLabel(text = stringResource(id = R.string.complete_account_gender_label))
        GENDER.values().forEach {
            Gender(
                gender = it,
                selected = state.gender == it,
                onClick = onGenderChanged,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        ScPremiumButton(
            enabled = state.canSubmit(),
            text = R.string.common_save,
            onClick = {
                focusManager.clearFocus()
                onSubmit()
            }
        )
    }
    if (state.isProcessing) {
        ProcessingOperationAnimation(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun CompleteAccountScreenPreview() {
    SkincareTheme {
        Surface {
            CompleteAccountScreen(
                CompleteAccountScreenViewState(),
                onNameChanged = {},
                onAgeChanged = {},
                onGenderChanged = {},
                onSubmit = {}
            )
        }
    }
}

@Composable
fun Gender(
    gender: GENDER,
    selected: Boolean,
    onClick: (GENDER) -> Unit
) {
    LeftRightComponent(
        leftComposable = {
            Text(
                text = stringResource(id = gender.getNameResId()),
                color = if (selected) Brown else BrownWhite50,
                style = MaterialTheme.typography.body2
            )
        },
        rightComposable = {
            RadioButton(
                selected = selected,
                onClick = { onClick(gender) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Brown,
                    unselectedColor = BrownWhite50
                )
            )
        },
    )
}

@Preview
@Composable
fun GenderUnselectedPreview() {
    SkincareTheme {
        Surface {
            Column {
                val selectedGender = remember { mutableStateOf(GENDER.FEMALE) }

                GENDER.values().forEach {
                    Gender(
                        gender = it,
                        selected = selectedGender.value == it,
                        onClick = { selectedGender.value = it },
                    )
                }
            }
        }
    }
}

