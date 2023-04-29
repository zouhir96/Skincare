package com.zrcoding.skincare.data.domain.model

import java.util.UUID

data class Category(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String
)
