package com.odensala.hashtalk.auth.presentation.screen.signup.error

import com.odensala.hashtalk.auth.domain.error.EmailError
import com.odensala.hashtalk.auth.domain.error.PasswordError

fun mapDomainEmailErrorToUi(domainError: EmailError): EmailFieldError =
    when (domainError) {
        EmailError.EMPTY -> EmailFieldError.Empty
        EmailError.INVALID -> EmailFieldError.Invalid
        EmailError.ALREADY_IN_USE -> EmailFieldError.AlreadyInUse
        EmailError.UNKNOWN -> EmailFieldError.Unknown
    }

fun mapDomainPasswordErrorToUi(domainError: PasswordError): PasswordFieldError =
    when (domainError) {
        PasswordError.EMPTY -> PasswordFieldError.Empty
        PasswordError.TOO_SHORT -> PasswordFieldError.TooShort
        PasswordError.NOT_MATCHING -> PasswordFieldError.NotMatching
        PasswordError.WEAK -> PasswordFieldError.Weak
        PasswordError.INVALID -> PasswordFieldError.Invalid
    }