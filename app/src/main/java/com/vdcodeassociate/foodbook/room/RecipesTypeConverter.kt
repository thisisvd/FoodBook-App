package com.vdcodeassociate.foodbook.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vdcodeassociate.foodbook.models.FoodItemResponse

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipes: FoodItemResponse): String {
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodItemResponse {
        val listType = object :TypeToken<FoodItemResponse>() {}.type
        return gson.fromJson(data,listType)
    }

}