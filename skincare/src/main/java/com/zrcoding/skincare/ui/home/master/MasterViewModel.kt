package com.zrcoding.skincare.ui.home.master

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.skincare.data.domain.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItemsCount = MutableStateFlow(0)
    val cartItemsCount: StateFlow<Int> = _cartItemsCount

    init {
        viewModelScope.launch {
            cartRepository.observeCurrentCart().collectLatest {
                _cartItemsCount.value = it.productCount()
            }
        }
    }
}