package com.odensala.hashtalk.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.odensala.hashtalk.auth.domain.error.PasswordError
import com.odensala.hashtalk.core.domain.error.Result
import org.junit.Before
import org.junit.Test

class ValidateRepeatPasswordUseCaseTest {

    private lateinit var validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase

    @Before
    fun setUp() {
        validateRepeatPasswordUseCase = ValidateRepeatPasswordUseCase()
    }

    @Test
    fun `Passwords do not match, returns NOT_MATCHING error`() {
        val result = validateRepeatPasswordUseCase("doraemon123", "nobita123")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(PasswordError.NOT_MATCHING)
    }

    @Test
    fun `Passwords match, returns success`() {
        val result = validateRepeatPasswordUseCase("doraemon123", "doraemon123")

        assertThat(result).isInstanceOf(Result.Success::class.java)
    }
}