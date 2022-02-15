package com.vdcodeassociate.foodbook.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.vdcodeassociate.foodbook.models.FoodItemResponse
import com.vdcodeassociate.foodbook.data.room.RecipesEntity
import com.vdcodeassociate.foodbook.ui.viewmodels.repository.Repository
import com.vdcodeassociate.foodbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    /** ROOM DATABASE */
    // read database from db
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    // insert recipes
    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }


    /** RETROFIT */
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

                // cache data
                val foodRecipe = recipeResponse.value!!.data
                if(foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }

            }catch (e: Exception){
                recipeResponse.value = Resource.Error("Recipes not found!")
            }
        }else {
            recipeResponse.value = Resource.Error("No Internet Connection!")
        }
    }

    // Cache data
    private fun offlineCacheRecipes(foodRecipe: FoodItemResponse) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
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
            response.body()!!.results.isEmpty() -> {
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