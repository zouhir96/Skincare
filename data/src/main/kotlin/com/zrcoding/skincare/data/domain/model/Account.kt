package com.zrcoding.skincare.data.domain.model

import java.util.UUID

data class Account(
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val gender: GENDER,
    val birthDay: Long,
    val imageUrl: String,
    val phoneNumber: String
) {
    companion object {
        // Useful for mocks and tests.
        val fake = Account(
            uuid = UUID.randomUUID().toString(),
            firstName = "Dua",
            lastName = "LIPA",
            gender = GENDER.FEMALE,
            birthDay = 677527751,
            imageUrl = "https://www.charlottetilbury.com/_next/static/images/Masterclass-placeholder-image-c9d6d1b2ca29a310fe41f6d12e1346d4.jpg",
            phoneNumber = "+212 666 666666"
        )
    }
}

// There is only two genders.
enum class GENDER {
    MALE, FEMALE
}
