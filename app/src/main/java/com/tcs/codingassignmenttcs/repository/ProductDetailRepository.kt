package com.tcs.codingassignmenttcs.repository

import com.tcs.codingassignmenttcs.api.ProductAPI
import com.tcs.codingassignmenttcs.database.ProductDao
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(private val productAPI: ProductAPI,private  val productDao: ProductDao) {



    suspend fun getProductDetail(productId:Int) : Flow<DataState<Product>> =
        flow {
            emit(DataState.Loading)
            try {
                val result = productAPI.getProductDetails(productId)
                if (result.isSuccessful && result.body() != null) {
                    emit(DataState.Success(result.body()!!))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }

    }

    suspend fun addProductToCart(product:Product) : Flow<DataState<Long>> =
        flow {
            emit(DataState.Loading)
            try {
                delay(500)
                val result = productDao.addProduct(product)
                if (result>0) {
                    emit(DataState.Success(result))
                }else{
                    emit(DataState.Success(-1))
                }

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
    }

}