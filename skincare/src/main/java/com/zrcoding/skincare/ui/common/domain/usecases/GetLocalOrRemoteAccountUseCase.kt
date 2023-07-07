package com.zrcoding.skincare.ui.common.domain.usecases

import com.zrcoding.skincare.data.domain.model.Account
import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import javax.inject.Inject

class GetLocalOrRemoteAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Account {
        val localAccount = accountRepository.getLocalAccount()
        return localAccount ?: accountRepository.getRemoteAccount()
    }
}