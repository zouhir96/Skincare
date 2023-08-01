package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Token

interface AuthenticationRepository {

    suspend fun login(email: String, password: String): Token

    suspend fun signup(email: String, password: String): Token
}