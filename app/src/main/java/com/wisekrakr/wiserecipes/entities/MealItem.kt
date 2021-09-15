package com.wisekrakr.wiserecipes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MealItems")
data class MealItem (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "mealId")
    @Expose
    @SerializedName("idMeal")
    val idMeal: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "name")
    @Expose
    @SerializedName("strMeal")
    val strMeal: String,

    @ColumnInfo(name = "thumbnail")
    @Expose
    @SerializedName("strMealThumb")
    val strMealThumb: String
)