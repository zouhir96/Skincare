package com.zrcoding.skincare.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zrcoding.skincare.R

val SOFIA_SANS = FontFamily(
    Font(resId = R.font.sofia_sans_medium),
    Font(resId = R.font.sofia_sans_medium, FontWeight.W600),
    Font(resId = R.font.sofia_sans_medium, FontWeight.W500)
)

// Set of Material typography styles to start with
val Typography = Typography(
    subtitle1 = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = SOFIA_SANS,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)