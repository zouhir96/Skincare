package com.zrcoding.skincare.ui.home.profile

sealed interface ProfileScreenViewState {
    object Loading : ProfileScreenViewState
    data class Info(
        val imageUrl: String,
        val fullName: String,
        val phoneNumber: String
    ) : ProfileScreenViewState
}