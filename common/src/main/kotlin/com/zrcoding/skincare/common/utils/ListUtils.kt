package com.zrcoding.skincare.common.utils

fun <T> List<T>.add(item: T) = toMutableList().apply { add(item) }.toList()

fun <T> List<T>.remove(item: T) = toMutableList().apply { remove(item) }.toList()

fun <T> List<T>.set(index: Int, item: T) = toMutableList().apply { set(index, item) }.toList()