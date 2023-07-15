package com.zrcoding.skincare.ui.common.domain.usecases

import com.zrcoding.skincare.common.domain.usecases.GetLocalOrRemoteAccountUseCase
import com.zrcoding.skincare.data.domain.model.Account
import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import com.zrcoding.skincare.ui.base.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetLocalOrRemoteAccountUseCaseTest : BaseTest() {


    @MockK
    lateinit var accountRepository: AccountRepository

    @InjectMockKs
    lateinit var useCase: GetLocalOrRemoteAccountUseCase


    @Test
    fun `getLocalOrRemoteAccount, local`() = runTest {
        // GIVEN
        val expectedAccount = Account.fake
        coEvery { accountRepository.getLocalAccount() } returns expectedAccount

        // WHEN
        val result = useCase()

        // THEN
        coVerify(exactly = 1) { accountRepository.getLocalAccount() }
        assertEquals(expectedAccount, result)
    }

    @Test
    fun `getLocalOrRemoteAccount, remote`() = runTest {
        // GIVEN
        val expectedAccount = Account.fake
        coEvery { accountRepository.getLocalAccount() } returns null
        coEvery { accountRepository.getRemoteAccount() } returns expectedAccount

        // WHEN
        val result = useCase()

        // THEN
        coVerify(exactly = 1) { accountRepository.getLocalAccount() }
        coVerify(exactly = 1) { accountRepository.getRemoteAccount() }
        assertEquals(expectedAccount, result)
    }

    @Test
    fun `getLocalOrRemoteAccount, error`() = runTest {
        // GIVEN
        val expectedError = mockk<Throwable>()
        coEvery { accountRepository.getLocalAccount() } returns null
        coEvery { accountRepository.getRemoteAccount() } throws expectedError

        // WHEN
        val result = runCatching {
            useCase()
        }

        // THEN
        coVerify(exactly = 1) { accountRepository.getLocalAccount() }
        coVerify(exactly = 1) { accountRepository.getRemoteAccount() }
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull(), expectedError)
    }
}