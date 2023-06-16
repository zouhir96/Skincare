package com.zrcoding.skincare.ui.common.domain.model

import com.zrcoding.skincare.data.domain.model.Category
import com.zrcoding.skincare.data.domain.model.Volume
import java.util.UUID

data class Filter(val id: String, val value: String)

val filterAll = Filter(UUID.randomUUID().toString(), "All")

@JvmName("categoryToFilter")
fun Category.toFilter() = Filter(uuid, name)

@JvmName("categoriesToFilters")
fun List<Category>.toFilters() = map { it.toFilter() }
    .toMutableList()
    .apply { add(0, filterAll) }
    .toList()

@JvmName("volumeToFilter")
fun Volume.toFilter() = Filter(uuid, "$value ${unit.name}")

@JvmName("volumesToFilters")
fun List<Volume>.toFilters() = map { it.toFilter() }