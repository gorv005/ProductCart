package com.tcs.codingassignmenttcs.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcs.codingassignmenttcs.models.Product
import com.tcs.codingassignmenttcs.utils.Constants.TABLE_NAME


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product): Long

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllProduct(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(list: List<Product>)

}