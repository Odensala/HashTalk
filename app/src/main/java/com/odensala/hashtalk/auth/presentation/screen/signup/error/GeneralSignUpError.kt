package com.odensala.hashtalk.auth.presentation.screen.signup.error

sealed class GeneralSignUpError {
    data object Unknown : GeneralSignUpError()

    data object Network : GeneralSignUpError()
}