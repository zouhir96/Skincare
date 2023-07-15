package com.zrcoding.skincare.ui.home.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.common.domain.usecases.GetLocalOrRemoteAccountUseCase
import com.zrcoding.skincare.common.utils.capitalize
import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getLocalOrRemoteAccountUseCase: GetLocalOrRemoteAccountUseCase
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ProfileScreenViewState>(ProfileScreenViewState.Loading)
    val viewState: StateFlow<ProfileScreenViewState> = _viewState

    private val _goToAuth = MutableSharedFlow<Unit>()
    val goToAuth: SharedFlow<Unit> = _goToAuth

    init {
        viewModelScope.launch {
            try {
                val account = getLocalOrRemoteAccountUseCase()
                _viewState.value = account.let {
                    ProfileScreenViewState.Info(
                        imageUrl = it.imageUrl,
                        fullName = "${it.firstName.capitalize()} ${it.lastName.uppercase()}",
                        gender = it.gender,
                        phoneNumber = it.phoneNumber
                    )
                }

            } catch (e: Exception) {
                // TODO disconnect user and navigate to auth.
            }
        }
    }

    fun onProfileImageUploaded(uri: Uri) {
        viewModelScope.launch {
            val newImageUrl = accountRepository.uploadNewImage(uri)
            _viewState.update {
                (it as ProfileScreenViewState.Info).copy(imageUrl = newImageUrl)
            }
        }
    }

    fun onLogoutClicked() {
        // TODO disconnect user
        viewModelScope.launch { _goToAuth.emit(Unit) }
    }
}