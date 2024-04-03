package com.tcs.codingassignmenttcs.api

import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPI {


    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<ProductListItem>



    @GET("products/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId: Int
    ): Response<Product>
}