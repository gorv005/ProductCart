package com.tcs.codingassignmenttcs.presentation.productdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.ProductDetailRepository
import com.tcs.codingassignmenttcs.utils.Constants
import com.tcs.codingassignmenttcs.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository) : ViewModel() {


    val productDetail: MutableState<DataState<Product>?> = mutableStateOf(null)
    val addToCartResult: MutableState<DataState<Long>?> = mutableStateOf(null)




    fun getProductDetails(id :Int) {

        viewModelScope.launch {
            productDetailRepository.getProductDetail(id).onEach {
            productDetail.value = it
            }.launchIn(viewModelScope)
        }
    }
   fun addToCart(product: Product) {
       viewModelScope.launch {
           productDetailRepository.addProductToCart(product)
               .onEach {
                   addToCartResult.value = it
           }.launchIn(viewModelScope)
       }
    }

}