package com.odensala.hashtalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import com.odensala.hashtalk.core.presentation.HashTalkApp
import com.odensala.hashtalk.core.presentation.theme.HashTalkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // For debug purposes
        FirebaseAuth.getInstance().signOut()
        enableEdgeToEdge()
        setContent {
            HashTalkTheme {
                HashTalkApp()
            }
        }
    }
}