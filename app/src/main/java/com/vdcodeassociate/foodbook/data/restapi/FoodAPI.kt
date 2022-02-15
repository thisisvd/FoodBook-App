package com.vdcodeassociate.foodbook.data.restapi

import com.vdcodeassociate.foodbook.models.FoodItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodAPI {

    @GET("/recipes/complexSearch")
    suspend fun getFoodRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodItemResponse>

}