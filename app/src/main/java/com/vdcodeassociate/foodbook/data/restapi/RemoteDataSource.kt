package com.vdcodeassociate.foodbook.data.restapi

import com.vdcodeassociate.foodbook.models.FoodItemResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodAPI: FoodAPI
) {

    // main recipe api request
    suspend fun getFoodRecipes(
        query: Map<String, String>
    ): Response<FoodItemResponse> {
        return foodAPI.getFoodRecipes(query)
    }

    // search a particular recipe form api
    suspend fun searchRecipe(
        searchQuery: Map<String, String>
    ): Response<FoodItemResponse> {
        return foodAPI.searchRecipes(searchQuery)
    }

}