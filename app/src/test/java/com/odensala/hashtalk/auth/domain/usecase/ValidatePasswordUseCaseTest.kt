package com.odensala.hashtalk.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.auth.domain.error.PasswordError
import com.odensala.hashtalk.core.domain.error.Result
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `Password with 7 characters, returns TOO_SHORT error`() {
        val result = validatePasswordUseCase("pw12345")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(PasswordError.TOO_SHORT)
    }

    @Test
    fun `Password missing letters or digits, returns INVALID error`() {
        val result = validatePasswordUseCase("notverygoodpassword")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(PasswordError.INVALID)
    }

    @Test
    fun `Valid password, returns success`() {
        val result = validatePasswordUseCase("password123")

        assertThat(result).isInstanceOf(Result.Success::class.java)
    }
}