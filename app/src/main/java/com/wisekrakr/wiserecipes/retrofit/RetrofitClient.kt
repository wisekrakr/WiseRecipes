package com.wisekrakr.wiserecipes.retrofit

import android.app.Activity
import com.wisekrakr.wiserecipes.BaseActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        private lateinit var retrofit: Retrofit
        private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        val retrofitInstance: Retrofit
            get(){
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
    }

}