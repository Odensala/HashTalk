package com.odensala.hashtalk.timeline.presentation.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun Timestamp.formatToDateString(pattern: String = "MMM d, yyyy, h:mm a"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this.toDate())
}