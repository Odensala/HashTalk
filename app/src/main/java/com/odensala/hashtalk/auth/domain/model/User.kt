package com.odensala.hashtalk.auth.domain.model

data class User(
    val uid: String,
    val email: String,
    val displayName: String?
)