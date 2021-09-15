package com.wisekrakr.wiserecipes.entities.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wisekrakr.wiserecipes.entities.Category
import com.wisekrakr.wiserecipes.entities.CategoryItem
import com.wisekrakr.wiserecipes.entities.MealItem

class MealListConverter {

    @TypeConverter
    fun fromMealList(meal: List<MealItem>): String? {


        val gson = Gson()
        val type = object : TypeToken<MealItem>(){

        }.type
        return gson.toJson(meal, type)
    }

    @TypeConverter
    fun toMealList(mealString: String): List<MealItem>? {

        val gson = Gson()
        val type = object : TypeToken<MealItem>(){

        }.type
        return gson.fromJson(mealString, type)
    }
}