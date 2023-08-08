package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.GENDER
import com.zrcoding.skincare.data.domain.model.Token

interface AuthenticationRepository {

    suspend fun login(email: String, password: String): Token

    suspend fun signup(email: String, password: String): Token

    suspend fun completeAccount(fullName: String, age: Int, gender: GENDER): Boolean
}