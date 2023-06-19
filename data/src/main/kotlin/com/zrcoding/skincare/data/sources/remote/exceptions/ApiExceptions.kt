package com.zrcoding.skincare.data.sources.remote.exceptions

sealed class PromoCodeException : Throwable() {
    object Expired : PromoCodeException()
    object Invalid : PromoCodeException()
}