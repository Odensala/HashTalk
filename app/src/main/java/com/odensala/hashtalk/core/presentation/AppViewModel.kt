package com.odensala.hashtalk.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val authState =
        authRepository.authState
            .map { result ->
                when (result) {
                    is Result.Success -> result.data
                    is Result.Error -> AuthState.Unauthenticated
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5000),
                initialValue = AuthState.CheckingAuthentication
            )
}