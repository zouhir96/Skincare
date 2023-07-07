package com.zrcoding.skincare.common.utils

import java.util.Locale

object StringUtils {
    const val EMPTY = ""
}

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
}