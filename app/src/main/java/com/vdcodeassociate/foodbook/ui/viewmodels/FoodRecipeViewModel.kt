package com.vdcodeassociate.foodbook.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vdcodeassociate.foodbook.constants.Constants
import com.vdcodeassociate.foodbook.constants.Constants.Companion.API_KEY
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_DIET_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_MEAL_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_API_KEY
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_DIET
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_NUMBER
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_TYPE
import com.vdcodeassociate.foodbook.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipeViewModel @Inject constructor (
    application: Application,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(application) {

    // default meal and diet assigned
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    // get save type & diet to datastore
    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    // save type & diet to datastore
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    // Queries for the api
    fun applyQueries(): HashMap<String,String> {
        val queries: HashMap<String,String> = HashMap()

        // always get values from data store first
        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        // adding query to pass the value to api
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}