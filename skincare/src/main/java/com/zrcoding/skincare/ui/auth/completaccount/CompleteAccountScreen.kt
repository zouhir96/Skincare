package com.zrcoding.skincare.ui.auth.completaccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zrcoding.skincare.R
import com.zrcoding.skincare.ui.components.LeftRightComponent
import com.zrcoding.skincare.ui.components.ScInputLabel
import com.zrcoding.skincare.ui.components.ScPremiumButton
import com.zrcoding.skincare.ui.components.ScTextField
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite50
import com.zrcoding.skincare.ui.theme.BrownWhite80
import com.zrcoding.skincare.ui.theme.SkincareTheme

@Composable
fun CompleteAccountRoute(
    viewModel: CompleteAccountScreenViewModel = hiltViewModel()
) {

}

@Composable
fun CompleteAccountScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 30.dp)
            .padding(horizontal = 25.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            ScInputLabel(text = stringResource(id = R.string.complete_account_name_label))
            Spacer(modifier = Modifier.height(16.dp))
            ScTextField(
                placeholder = R.string.complete_account_name_placeholder,
                text = "",
                onValueChanged = {}
            )

            var sliderValue by remember {
                mutableStateOf(15f)
            }
            Spacer(modifier = Modifier.height(30.dp))
            LeftRightComponent(
                leftComposable = {
                    ScInputLabel(text = stringResource(id = R.string.complete_account_age_label))
                },
                rightComposable = {
                    Text(
                        text = stringResource(
                            id = R.string.complete_account_age_value,
                            sliderValue.toInt()
                        ),
                        color = BrownWhite50,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            )
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                valueRange = 15f..90f,
                steps = 75,
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
            Column(
                modifier = Modifier.selectable(
                    selected = true,
                    onClick = {}
                )
            ) {
                Gender(
                    text = stringResource(id = R.string.complete_account_gender_woman),
                    selected = false,
                    onClick = {}
                )
                Gender(
                    text = stringResource(id = R.string.complete_account_gender_man),
                    selected = true,
                    onClick = {}
                )
            }
        }
        ScPremiumButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = R.string.common_save
        ) {

        }
    }
}

@Preview
@Composable
fun CompleteAccountScreenPreview() {
    SkincareTheme {
        Surface {
            CompleteAccountScreen()
        }
    }
}

@Composable
fun Gender(
    text: String,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    LeftRightComponent(
        leftComposable = {
            Text(
                text = text,
                color = if (selected) Brown else BrownWhite50,
                style = MaterialTheme.typography.body2
            )
        },
        rightComposable = {
            RadioButton(
                selected = selected,
                onClick = { onClick(text) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Brown,
                    unselectedColor = BrownWhite50
                )
            )
        }
    )
}

@Preview
@Composable
fun GenderUnselectedPreview() {
    SkincareTheme {
        Surface {
            Column {
                Gender(
                    text = stringResource(id = R.string.complete_account_gender_woman),
                    selected = false,
                    onClick = {}
                )
                Gender(
                    text = stringResource(id = R.string.complete_account_gender_woman),
                    selected = true,
                    onClick = {}
                )
            }
        }
    }
}

