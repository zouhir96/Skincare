package com.zrcoding.skincare.common.domain.usecases


import com.zrcoding.skincare.ui.base.BaseTest
import io.mockk.impl.annotations.InjectMockKs
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

import org.junit.Test

class FormValidatorUseCaseTest : BaseTest() {

    @InjectMockKs
    lateinit var useCaseTest: FormValidatorUseCase

    @Test
    fun `validateEmail, empty`() {
        // GIVEN
        val typedEmail = ""

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.EMPTY)
    }

    @Test
    fun `validateEmail, uncompleted email`() {
        // GIVEN
        val typedEmail = "jhon.doe"

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.INVALID)
    }

    @Test
    fun `validateEmail, email without @`() {
        // GIVEN
        val typedEmail = "jhon.doegmail.com"

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.INVALID)
    }

    @Test
    fun `validateEmail, email without org`() {
        // GIVEN
        val typedEmail = "jhon.doe@"

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.INVALID)
    }

    @Test
    fun `validateEmail, email without domain name`() {
        // GIVEN
        val typedEmail = "jhon.doe@gmail"

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.INVALID)
    }

    @Test
    fun `validateEmail, valid`() {
        // GIVEN
        val typedEmail = "jhon.doe@gmail.com"

        // WHEN
        val result = useCaseTest.validateEmail(typedEmail)

        // THEN
        assertEquals(result, FormValidatorUseCase.Result.VALID)
    }

    @Test
    fun `validatePassword, 8 characters`() {
        // GIVEN
        val typedEmail = "aaaaaaaa"

        // WHEN
        val result = useCaseTest.validatePassword(typedEmail)

        // THEN
        assertTrue(result.passwordConstraint8Characters)
    }

    @Test
    fun `validatePassword, 1 uppercase`() {
        // GIVEN
        val typedEmail = "Aaaaaaaa"

        // WHEN
        val result = useCaseTest.validatePassword(typedEmail)

        // THEN
        assertTrue(result.passwordConstraint1Uppercase)
    }

    @Test
    fun `validatePassword, 1 symbol`() {
        // GIVEN
        val typedEmail = "Aaaa@aaaa"

        // WHEN
        val result = useCaseTest.validatePassword(typedEmail)

        // THEN
        assertTrue(result.passwordConstraint1Uppercase)
    }

    @Test
    fun `validatePassword, 1 number`() {
        // GIVEN
        val typedEmail = "Aaaaaaa1a"

        // WHEN
        val result = useCaseTest.validatePassword(typedEmail)

        // THEN
        assertTrue(result.passwordConstraint1Uppercase)
    }

    @Test
    fun `validatePassword, all valid`() {
        // GIVEN
        val typedEmail = "Mypassword@1996"

        // WHEN
        val result = useCaseTest.validatePassword(typedEmail)

        // THEN
        assertTrue(result.areAllValid())
    }
}