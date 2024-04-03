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
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class CartRepository @Inject constructor(private  val productDao: ProductDao) {

    suspend fun getAllCartProduct() : Flow<DataState<List<Product>>> =
        flow {
            emit(DataState.Loading)
            try {
                delay(500)
                val result = productDao.getAllProduct()
                if (result.size > 0) {
                    emit(DataState.Success(result))
                } else {
                    emit(DataState.Success(emptyList()))
                }

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

}