package com.tcs.codingassignmenttcs.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tcs.codingassignmenttcs.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Product(
    @SerializedName("title"              ) var title              : String?           = null,
    @SerializedName("description"        ) var description        : String?           = null,
    @SerializedName("price"              ) var price              : Int?              = null,
    @SerializedName("discountPercentage" ) var discountPercentage : Double?           = null,
    @SerializedName("rating"             ) var rating             : Double?           = null,
    @SerializedName("stock"              ) var stock              : Int?              = null,
    @SerializedName("brand"              ) var brand              : String?           = null,
    @SerializedName("category"           ) var category           : String?           = null,
    @SerializedName("thumbnail"          ) var thumbnail          : String?           = null,
){

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    var id: Int? = null

    @Ignore
    @SerializedName("images")
    var images: List<String>? = null
}