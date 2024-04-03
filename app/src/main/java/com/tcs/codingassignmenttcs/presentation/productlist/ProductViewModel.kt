package com.tcs.codingassignmenttcs.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tcs.codingassignmenttcs.domain.usecase.GetProductsUseCase
import com.tcs.codingassignmenttcs.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) : ViewModel() {
    private val _productState: MutableStateFlow<PagingData<Product>> = MutableStateFlow(value = PagingData.empty())


    val products: MutableStateFlow<PagingData<Product>> get() = _productState

    init {
        onEvent(ProductListEvent.GetProduct)

        viewModelScope.launch {
            onEvent(ProductListEvent.GetProduct)
        }
    }
    fun onEvent(event: ProductListEvent) {
        viewModelScope.launch {
            when (event) {
                is ProductListEvent.GetProduct -> {
                    getProducts()
                }
            }
        }
    }

    public suspend fun getProducts() {
        getProductsUseCase.execute(Unit)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _productState.value = it
            }
    }
    sealed class ProductListEvent {
        object GetProduct : ProductListEvent()
    }
}