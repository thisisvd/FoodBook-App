package com.vdcodeassociate.foodbook.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ResourceCursorAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vdcodeassociate.foodbook.models.FoodItem
import com.vdcodeassociate.foodbook.models.FoodItemResponse
import com.vdcodeassociate.foodbook.ui.viewmodels.repository.Repository
import com.vdcodeassociate.foodbook.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Query
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    // food response variable
    var recipeResponse: MutableLiveData<Resource<FoodItemResponse>> = MutableLiveData()

    // calling the response method / data
    fun getFoodItem(query: Map<String, String>) = viewModelScope.launch {
        getFoodSafeCall(query)
    }

    // getting save callback from network with network active status
    private suspend fun getFoodSafeCall(query: Map<String, String>) {
        recipeResponse.value = Resource.Loading()
        if(hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodRecipes(query)
                recipeResponse.value = handleRecipeResponse(response)
            }catch (e: Exception){
                recipeResponse.value = Resource.Error("Recipes not found!")
            }
        }else {
            recipeResponse.value = Resource.Error("No Internet Connection!")
        }
    }

    // handling error responses
    private fun handleRecipeResponse(response: Response<FoodItemResponse>): Resource<FoodItemResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }
            response.code() == 402 -> {
                return Resource.Error("API KEY Limited!")
            }
            response.body()!!.results.isNotEmpty() -> {
                return Resource.Error("Food Item / Recipe not found!")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return Resource.Success(foodRecipes!!)
            }
            else -> {
                return Resource.Error(response.message())
            }
        }
    }

    // check for active internet connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }

}