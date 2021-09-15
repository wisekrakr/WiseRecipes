package com.wisekrakr.wiserecipes.dao

import androidx.room.*
import com.wisekrakr.wiserecipes.entities.Category
import com.wisekrakr.wiserecipes.entities.CategoryItem
import com.wisekrakr.wiserecipes.entities.MealItem
import com.wisekrakr.wiserecipes.entities.Recipe

// Same as a repository in Spring
// data access object. Define database interactions here. Room will generate an implementation
// of this when it is referenced by the Database

@Dao
interface RecipeDao {


    // clears all
    @Query("DELETE FROM categoryitems")
    suspend fun cleaDB()



    // RECIPES
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    suspend fun getAllRecipes() : List<Recipe>

    /**
     * Returns the inserted rows ids. Will never return -1 since this strategy will always insert a
     * row even if there is a conflict
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe?)

    //CATEGORIES
    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    suspend fun getAllCategories() : List<CategoryItem>

    /**
     * Returns the inserted rows ids. Will never return -1 since this strategy will always insert a
     * row even if there is a conflict
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryItem: CategoryItem?)


    //Meals
    @Query("SELECT * FROM mealitems WHERE category = :category ORDER BY id DESC")
    suspend fun getAllMealsByCategory(category: String) : List<MealItem>

    @Query("SELECT * FROM mealitems ORDER BY id DESC")
    suspend fun getAllMeals() : List<MealItem>

    /**
     * Returns the inserted rows ids. Will never return -1 since this strategy will always insert a
     * row even if there is a conflict
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeal(mealItem: MealItem?)


}