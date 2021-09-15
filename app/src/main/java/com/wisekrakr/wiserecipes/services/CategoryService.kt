package com.wisekrakr.wiserecipes.services

import com.wisekrakr.wiserecipes.entities.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("categories.php")
    fun getCategories(): Call<Category>

}