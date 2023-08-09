package com.zrcoding.skincare.features.connected.home.profile

import com.zrcoding.skincare.data.domain.model.GENDER

sealed interface ProfileScreenViewState {
    object Loading : ProfileScreenViewState
    data class Info(
        val imageUrl: String,
        val fullName: String,
        val gender: GENDER,
        val phoneNumber: String
    ) : ProfileScreenViewState
}