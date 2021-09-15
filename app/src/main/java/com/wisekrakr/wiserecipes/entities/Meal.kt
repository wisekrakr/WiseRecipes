package com.wisekrakr.wiserecipes.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wisekrakr.wiserecipes.entities.converter.MealListConverter

@Entity(tableName = "Meals")
data class Meal(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "mealItems")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter::class)
    val meals: List<MealItem>
)

