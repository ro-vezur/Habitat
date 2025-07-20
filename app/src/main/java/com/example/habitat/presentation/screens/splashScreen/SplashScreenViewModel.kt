package com.example.habitat.presentation.screens.splashScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _isUserCompletedRegistration: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserCompletedRegistration: StateFlow<Boolean> = _isUserCompletedRegistration.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.isCompletedRegistration.collectLatest { isCompleted ->
                _isUserCompletedRegistration.update { isCompleted }
            }
        }
    }
}