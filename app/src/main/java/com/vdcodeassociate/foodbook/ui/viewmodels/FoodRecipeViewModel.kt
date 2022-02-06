package com.vdcodeassociate.foodbook.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vdcodeassociate.foodbook.constants.Constants
import com.vdcodeassociate.foodbook.constants.Constants.Companion.API_KEY
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_API_KEY
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_DIET
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_NUMBER
import com.vdcodeassociate.foodbook.constants.Constants.Companion.QUERY_TYPE

class FoodRecipeViewModel (
    application: Application
): AndroidViewModel(application) {

    // Queries
    fun applyQueries(): HashMap<String,String> {
        val queries: HashMap<String,String> = HashMap()

        queries[QUERY_NUMBER] = "20"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}