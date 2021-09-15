package com.wisekrakr.wiserecipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.wisekrakr.wiserecipes.entities.MealResponse
import com.wisekrakr.wiserecipes.retrofit.RetrofitClient
import com.wisekrakr.wiserecipes.services.MealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailActivity : BaseActivity() {


    private var youtubeLink = ""
    private var imgBtnBack : ImageButton? = null
    private var btnYoutube : Button? = null
    private var imgItem : RoundedImageView? = null
    private var tvMealName : TextView? = null
    private var tvCategory : TextView? = null
    private var tvArea : TextView? = null
    private var tvIngredients : TextView? = null
    private var tvInstructions : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        imgBtnBack = findViewById(R.id.img_btn_back)
        btnYoutube = findViewById(R.id.btn_youtube)
        imgItem = findViewById(R.id.img_item)
        tvMealName = findViewById(R.id.tv_meal_name)
        tvCategory = findViewById(R.id.tv_category)
        tvArea = findViewById(R.id.tv_area)
        tvIngredients = findViewById(R.id.tv_ingredients)
        tvInstructions = findViewById(R.id.tv_instructions)

        val id = intent.getStringExtra("id")

        getMealDetails(id!!)

        imgBtnBack!!.setOnClickListener {
            finish()
        }

        btnYoutube!!.setOnClickListener {
            val uri = Uri.parse(youtubeLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }


    fun getMealDetails(id:String) {
        val service = RetrofitClient.retrofitInstance.create(MealService::class.java)
        val call = service.getMealById(id)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {
                if (response.code() == 200) {
                    Glide.with(this@MealDetailActivity).load(response.body()!!.mealEntities[0].strmealthumb).into(
                        imgItem!!
                    )
                    tvMealName!!.text = response.body()!!.mealEntities[0].strmeal

                    val ingredients = showIngredients(response)

                    tvCategory!!.text = response.body()!!.mealEntities[0].strcategory
                    tvArea!!.text = response.body()!!.mealEntities[0].strarea

                    tvIngredients!!.text = ingredients
                    tvInstructions!!.text = response.body()!!.mealEntities[0].strinstructions

                    youtubeLink = response.body()!!.mealEntities[0].strsource

                } else {
                    Toast.makeText(
                        this@MealDetailActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(
                    this@MealDetailActivity,
                    "There was an oopsie, please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    
    private fun showIngredients(response: Response<MealResponse>): String {
        return "${response.body()!!.mealEntities[0].stringredient1}      ${response.body()!!.mealEntities[0].strmeasure1}\n" +
                "${response.body()!!.mealEntities[0].stringredient2}      ${response.body()!!.mealEntities[0].strmeasure2}\n" +
                "${response.body()!!.mealEntities[0].stringredient3}      ${response.body()!!.mealEntities[0].strmeasure3}\n" +
                "${response.body()!!.mealEntities[0].stringredient4}      ${response.body()!!.mealEntities[0].strmeasure4}\n" +
                "${response.body()!!.mealEntities[0].stringredient5}      ${response.body()!!.mealEntities[0].strmeasure5}\n" +
                "${response.body()!!.mealEntities[0].stringredient6}      ${response.body()!!.mealEntities[0].strmeasure6}\n" +
                "${response.body()!!.mealEntities[0].stringredient7}      ${response.body()!!.mealEntities[0].strmeasure7}\n" +
                "${response.body()!!.mealEntities[0].stringredient8}      ${response.body()!!.mealEntities[0].strmeasure8}\n" +
                "${response.body()!!.mealEntities[0].stringredient9}      ${response.body()!!.mealEntities[0].strmeasure9}\n" +
                "${response.body()!!.mealEntities[0].stringredient10}      ${response.body()!!.mealEntities[0].strmeasure10}\n" +
                "${response.body()!!.mealEntities[0].stringredient11}      ${response.body()!!.mealEntities[0].strmeasure11}\n" +
                "${response.body()!!.mealEntities[0].stringredient12}      ${response.body()!!.mealEntities[0].strmeasure12}\n" +
                "${response.body()!!.mealEntities[0].stringredient13}      ${response.body()!!.mealEntities[0].strmeasure13}\n" +
                "${response.body()!!.mealEntities[0].stringredient14}      ${response.body()!!.mealEntities[0].strmeasure14}\n" +
                "${response.body()!!.mealEntities[0].stringredient15}      ${response.body()!!.mealEntities[0].strmeasure15}\n" +
                "${response.body()!!.mealEntities[0].stringredient16}      ${response.body()!!.mealEntities[0].strmeasure16}\n" +
                "${response.body()!!.mealEntities[0].stringredient17}      ${response.body()!!.mealEntities[0].strmeasure17}\n" +
                "${response.body()!!.mealEntities[0].stringredient18}      ${response.body()!!.mealEntities[0].strmeasure18}\n" +
                "${response.body()!!.mealEntities[0].stringredient19}      ${response.body()!!.mealEntities[0].strmeasure19}\n" +
                "${response.body()!!.mealEntities[0].stringredient20}      ${response.body()!!.mealEntities[0].strmeasure20}\n"
    }
}