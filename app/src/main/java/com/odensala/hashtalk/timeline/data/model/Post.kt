package com.odensala.hashtalk.timeline.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Post(
    @DocumentId val id: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val content: String = "",
    val imageUrl: String? = null,
    val timestamp: Timestamp = Timestamp.now()
)