package com.tcs.codingassignmenttcs.models

import com.google.gson.annotations.SerializedName

data class ProductListItem(
    @SerializedName("products" ) var products: List<Product> = arrayListOf(),
    @SerializedName("total"    ) var total    : Int?                = null,
    @SerializedName("skip"     ) var skip     : Int?                = null,
    @SerializedName("limit"    ) var limit    : Int?                = null
)