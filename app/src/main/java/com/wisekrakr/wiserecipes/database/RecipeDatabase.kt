package com.wisekrakr.wiserecipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wisekrakr.wiserecipes.dao.RecipeDao
import com.wisekrakr.wiserecipes.entities.*
import com.wisekrakr.wiserecipes.entities.converter.CategoryListConverter
import com.wisekrakr.wiserecipes.entities.converter.MealListConverter


@Database(entities = [
    Recipe::class, Category::class, CategoryItem::class, Meal::class, MealItem::class],
    version = 1, exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase(){

    companion object{
        private var recipeDatabase: RecipeDatabase? = null

        /**
         * This method will be protected from concurrent execution by multiple threads by the monitor
         * of the instance on which the method is defined
         * @param context allows access to application-specific resources, classes, launching activities etc.
         */
        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if(recipeDatabase == null){
                recipeDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }
            return recipeDatabase!!
        }
    }

    abstract fun recipeDao(): RecipeDao
}