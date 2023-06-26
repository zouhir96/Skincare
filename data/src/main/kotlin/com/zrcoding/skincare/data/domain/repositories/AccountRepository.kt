package com.zrcoding.skincare.data.domain.repositories

import com.zrcoding.skincare.data.domain.model.Account
import java.io.File

interface AccountRepository {

    suspend fun getLocalAccount(): Account?

    suspend fun getRemoteAccount(): Account

    suspend fun uploadNewImage(file: File): String

    suspend fun updateAccount(account: Account): Account
}