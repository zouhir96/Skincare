package com.zrcoding.skincare.ui.auth.completaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.data.domain.model.GENDER
import com.zrcoding.skincare.data.domain.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteAccountScreenViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(CompleteAccountScreenViewState())
    val viewState = _viewState.asStateFlow()

    private val _isCompleted = MutableSharedFlow<Unit>()
    val isCompleted = _isCompleted.asSharedFlow()
    fun onNameChanged(name: String) {
        _viewState.update { it.copy(fullName = name) }
    }

    fun onAgeChanged(age: Float) {
        _viewState.update { it.copy(age = age.toInt()) }
    }

    fun onGenderChanged(gender: GENDER) {
        _viewState.update { it.copy(gender = gender) }
    }

    fun onSubmit() {
        val (fullName, age, gender) = _viewState.value
        _viewState.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            val completed = authenticationRepository.completeAccount(fullName, age, gender)
            _viewState.update { it.copy(isProcessing = false) }
            if (completed) {
                _isCompleted.emit(Unit)
            } else {
                // TODO handel error
            }
        }
    }
}