package com.wisekrakr.wiserecipes.entities.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wisekrakr.wiserecipes.entities.Category
import com.wisekrakr.wiserecipes.entities.CategoryItem

class CategoryListConverter {

    @TypeConverter
    fun fromCategoryList(category: List<CategoryItem>): String? {


        val gson = Gson()
        val type = object : TypeToken<CategoryItem>(){

        }.type
        return gson.toJson(category, type)
    }

    @TypeConverter
    fun toCategoryList(categoryString: String): List<CategoryItem>? {

        val gson = Gson()
        val type = object : TypeToken<CategoryItem>(){

        }.type
        return gson.fromJson(categoryString, type)
    }
}