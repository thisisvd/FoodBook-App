package com.vdcodeassociate.foodbook.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vdcodeassociate.foodbook.constants.Constants.Companion.RECIPES_TABLE
import com.vdcodeassociate.foodbook.models.FoodItemResponse

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
  var foodRecipes: FoodItemResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}