package com.zrcoding.skincare.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ProfileScreenViewState>(ProfileScreenViewState.Loading)
    val viewState: StateFlow<ProfileScreenViewState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = ProfileScreenViewState.Info(
                imageUrl = "",
                fullName = "Dua LIPA",
                phoneNumber = "+212 622 222222"
            )
        }
    }
}