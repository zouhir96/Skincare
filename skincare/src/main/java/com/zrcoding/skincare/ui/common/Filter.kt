package com.zrcoding.skincare.ui.common

import com.zrcoding.skincare.data.domain.model.Category
import java.util.UUID

data class Filter(val id: String, val value: String)

val filterAll = Filter(UUID.randomUUID().toString(), "All")

fun Category.toFilter() = Filter(uuid, name)
