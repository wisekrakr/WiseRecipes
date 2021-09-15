package com.wisekrakr.wiserecipes

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisekrakr.wiserecipes.adapter.CategoryAdapter
import com.wisekrakr.wiserecipes.adapter.MealAdapter
import com.wisekrakr.wiserecipes.database.RecipeDatabase
import com.wisekrakr.wiserecipes.databinding.ActivityHomeBinding
import com.wisekrakr.wiserecipes.entities.CategoryItem
import com.wisekrakr.wiserecipes.entities.MealItem
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity() {

    private var arrayCategories = ArrayList<CategoryItem>()
    private var arrayMeals = ArrayList<MealItem>()
    private var arrayAllMeals = ArrayList<MealItem>()

    private var categoryAdapter = CategoryAdapter()
    private var mealAdapter = MealAdapter()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategoryData()
        getAllMeals()

        categoryAdapter.setClickListener(onCategoryClick)
        mealAdapter.setClickListener(onMealClick)


        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val meals = arrayAllMeals

                val tempArray = ArrayList<MealItem>()

                for (meal in meals) {
                    if (meal.strMeal.toLowerCase(Locale.getDefault()).contains(newText.toString()))
                        tempArray.add(meal)
                }

                mealAdapter.setData(tempArray)
                return true
            }

        })


    }

    private val onCategoryClick = object : CategoryAdapter.OnItemClickListener {
        override fun onClick(categoryName: String) {
            getMealData(categoryName)
        }
    }

    private val onMealClick = object : MealAdapter.OnItemClickListener {
        override fun onClick(id: String) {
            val intent = Intent(this@HomeActivity, MealDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }
    }

    private fun getAllMeals(){
        launch {
            this.let {
                val meals = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllMeals()
                arrayAllMeals = meals as ArrayList<MealItem>
            }
        }
    }

    private fun getCategoryData() {
        launch {
            this.let {
                val categories =
                    RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategories()
                arrayCategories = categories as ArrayList<CategoryItem>

                arrayCategories.reverse()
                getMealData(arrayCategories[0].strCategory)

                categoryAdapter.setData(arrayCategories)

                binding.rvCategories.layoutManager = LinearLayoutManager(
                    this@HomeActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.rvCategories.adapter = categoryAdapter
            }
        }
    }

    private fun getMealData(category: String) {
        binding.tvCategoryTitle.text = "Recipes for " + category.toUpperCase()
        launch {
            this.let {
                val meals = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao()
                    .getAllMealsByCategory(category)
                arrayMeals = meals as ArrayList<MealItem>

                mealAdapter.setData(arrayMeals)

                binding.rvMeals.layoutManager = LinearLayoutManager(
                    this@HomeActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.rvMeals.adapter = mealAdapter


            }
        }
    }


}