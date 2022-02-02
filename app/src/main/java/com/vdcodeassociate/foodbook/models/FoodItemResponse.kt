package com.vdcodeassociate.foodbook.models

import com.google.gson.annotations.SerializedName

data class FoodItemResponse(
    @SerializedName("results")
    val results: List<FoodItem>
)
