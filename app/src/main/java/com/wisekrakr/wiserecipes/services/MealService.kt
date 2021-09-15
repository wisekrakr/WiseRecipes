package com.wisekrakr.wiserecipes.services

import com.wisekrakr.wiserecipes.entities.Meal
import com.wisekrakr.wiserecipes.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<Meal>

    @GET("filter.php")
    fun getMealsByArea(@Query("a") area: String): Call<Meal>

    @GET("search.php")
    fun getMealByName(@Query("s") meal: String): Call<Meal>

    @GET("lookup.php")
    fun getMealById(@Query("i") id: String): Call<MealResponse>
}