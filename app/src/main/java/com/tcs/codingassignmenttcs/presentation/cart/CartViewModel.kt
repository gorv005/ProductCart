package com.tcs.codingassignmenttcs.presentation.cart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.CartRepository
import com.tcs.codingassignmenttcs.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : ViewModel() {

    val cartProductList: MutableState<DataState<List<Product>>?> = mutableStateOf(null)

    
    fun getAllProducts() {
        viewModelScope.launch {
            cartRepository.getAllCartProduct()
                .onEach {
                    cartProductList.value = it
                }.launchIn(viewModelScope)
        }
    }
}