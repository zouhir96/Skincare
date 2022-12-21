package com.zrcoding.skincare.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zrcoding.skincare.ui.theme.Brown
import com.zrcoding.skincare.ui.theme.BrownWhite50

@Composable
fun HorizontalSteps(
    index: Int = 0
) {
    Row {
        Spacer(
            modifier = Modifier
                .width(30.dp)
                .height(3.dp)
                .background(color = if (index == 0) Brown else BrownWhite50)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Spacer(
            modifier = Modifier
                .width(30.dp)
                .height(3.dp)
                .background(color = if (index == 1) Brown else BrownWhite50)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Spacer(
            modifier = Modifier
                .width(30.dp)
                .height(3.dp)
                .background(color = if (index == 2) Brown else BrownWhite50)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Spacer(
            modifier = Modifier
                .width(30.dp)
                .height(3.dp)
                .background(color = if (index == 3) Brown else BrownWhite50)
        )
    }
}