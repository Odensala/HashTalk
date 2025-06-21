package com.odensala.hashtalk.auth.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.odensala.hashtalk.auth.domain.model.User

fun FirebaseUser.toUser(): User {
    return User(
        uid = uid,
        email = email ?: "",
        displayName = displayName
    )
}