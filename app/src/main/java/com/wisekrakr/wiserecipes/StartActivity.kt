package com.wisekrakr.wiserecipes

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.wisekrakr.wiserecipes.database.RecipeDatabase
import com.wisekrakr.wiserecipes.databinding.ActivityStartBinding
import com.wisekrakr.wiserecipes.entities.Category
import com.wisekrakr.wiserecipes.entities.Meal
import com.wisekrakr.wiserecipes.entities.MealItem
import com.wisekrakr.wiserecipes.services.CategoryService
import com.wisekrakr.wiserecipes.retrofit.RetrofitClient
import com.wisekrakr.wiserecipes.services.MealService
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private var READ_STORAGE_PERMISSION = 123
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readStorage()

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this@StartActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun getCategories() {
        val service = RetrofitClient.retrofitInstance.create(CategoryService::class.java)
        val call = service.getCategories()

        call.enqueue(object : Callback<Category> {
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                if (response.code() == 200) {
                    insertCategoriesIntoDatabase(response.body())

                    for (category in response.body()!!.categories!!){
                        getMeals(category.strCategory)
                    }
                } else {
                    Toast.makeText(
                        this@StartActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                binding.pbLoader.visibility = INVISIBLE
                Toast.makeText(
                    this@StartActivity,
                    "There was an oopsie, please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getMeals(category: String) {
        val service = RetrofitClient.retrofitInstance.create(MealService::class.java)
        val call = service.getMealsByCategory(category)

        call.enqueue(object : Callback<Meal> {
            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                if (response.code() == 200) {
                    insertMealsIntoDatabase(category,response.body())
                } else {
                    Toast.makeText(
                        this@StartActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                binding.pbLoader.visibility = INVISIBLE
                Toast.makeText(
                    this@StartActivity,
                    "There was an oopsie, please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun insertCategoriesIntoDatabase(body: Category?) {
        launch {
            this.let {
                for (arr in body!!.categories!!) {
                    RecipeDatabase.getDatabase(this@StartActivity)
                        .recipeDao().addCategory(arr)
                }
            }
        }
    }

    private fun insertMealsIntoDatabase(category: String, body: Meal?) {
        launch {
            this.let {
                for (arr in body!!.meals) {
                    RecipeDatabase.getDatabase(this@StartActivity)
                        .recipeDao().addMeal(MealItem(
                            arr.id,
                            arr.idMeal,
                            category,
                            arr.strMeal,
                            arr.strMealThumb
                        ))
                }
                binding.btnGetStarted.visibility = VISIBLE
                binding.pbLoader.visibility = INVISIBLE
            }
        }
    }

    private fun clearDatabase() {
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@StartActivity).recipeDao().cleaDB()
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorage() {
        if (hasReadStoragePermission()) {
            clearDatabase() // clear the database
            getCategories() // then fill it up again

        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app is asking permission to read storage",
                READ_STORAGE_PERMISSION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}