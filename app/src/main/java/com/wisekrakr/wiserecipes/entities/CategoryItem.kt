package com.wisekrakr.wiserecipes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CategoryItems")
data class CategoryItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "categoryId")
    @Expose
    @SerializedName("idCategory")
    val idCategory: String,

    @ColumnInfo(name = "name")
    @Expose
    @SerializedName("strCategory")
    val strCategory: String,

    @ColumnInfo(name = "description")
    @Expose
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String,

    @ColumnInfo(name = "thumbnail")
    @Expose
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String
)
