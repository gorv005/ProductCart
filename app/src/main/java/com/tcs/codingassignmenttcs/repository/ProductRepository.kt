package com.tcs.codingassignmenttcs.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tcs.codingassignmenttcs.api.ProductAPI
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.repository.paging.ProductPagingSource
import com.tcs.codingassignmenttcs.utils.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productAPI: ProductAPI) {


    suspend fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductPagingSource(productAPI)
            }
        ).flow
    }
}