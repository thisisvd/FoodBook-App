package com.vdcodeassociate.foodbook.data.restapi

import com.vdcodeassociate.foodbook.models.FoodItemResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodAPI: FoodAPI
) {

    suspend fun getFoodRecipes(
        query: Map<String, String>
    ): Response<FoodItemResponse> {
        return foodAPI.getFoodRecipes(query)
    }

}