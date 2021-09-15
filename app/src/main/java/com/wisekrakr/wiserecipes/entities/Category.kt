package com.wisekrakr.wiserecipes.entities

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wisekrakr.wiserecipes.entities.converter.CategoryListConverter

@Entity(tableName = "Categories")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "categoryItems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)
    val categories: List<CategoryItem>? = null
)

