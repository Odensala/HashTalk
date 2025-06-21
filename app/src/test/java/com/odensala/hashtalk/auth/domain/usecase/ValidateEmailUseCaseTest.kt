package com.odensala.hashtalk.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.auth.domain.error.EmailError
import com.odensala.hashtalk.core.domain.error.Result
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `Email is empty, returns empty error`() {
        val result = validateEmailUseCase("  ")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(EmailError.EMPTY)
    }

    @Test
    fun `Valid email, returns success`() {
        val result = validateEmailUseCase("doraemon@example.com")

        assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `Various invalid email formats, return invalid error`() {
        val invalidEmails = listOf(
            "missing-at-symbol.com",
            "@missing-local.com",
            "missing-domain@",
            "missing@dot",
            "spaces @domain.com",
            "double@@domain.com"
        )

        invalidEmails.forEach { email ->
            val result = validateEmailUseCase(email)

            assertThat(result).isInstanceOf(Result.Error::class.java)
            assertThat((result as Result.Error).error).isEqualTo(EmailError.INVALID)
        }
    }
}