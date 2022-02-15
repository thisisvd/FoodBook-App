package com.vdcodeassociate.foodbook.data.restapi

import com.vdcodeassociate.foodbook.models.FoodItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodAPI {

    // main search request
    @GET("/recipes/complexSearch")
    suspend fun getFoodRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodItemResponse>

    // search a recipe
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodItemResponse>

}