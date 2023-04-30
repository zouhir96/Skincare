package com.zrcoding.skincare.data.domain.model

import java.util.UUID

data class Volume(
    val uuid: String = UUID.randomUUID().toString(),
    val value: Int,
    val unit: VolumeUnit = VolumeUnit.ML
)

enum class VolumeUnit {
    ML
}
