package com.vdcodeassociate.foodbook.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.vdcodeassociate.foodbook.models.FoodItemResponse
import com.vdcodeassociate.foodbook.data.room.RecipesEntity
import com.vdcodeassociate.foodbook.models.FoodItem
import com.vdcodeassociate.foodbook.ui.fragments.RecipesFragment
import com.vdcodeassociate.foodbook.ui.fragments.RecipesFragmentDirections
import com.vdcodeassociate.foodbook.utils.Resource
import java.lang.Exception

class RecipesBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(foodRecipesLayout: ConstraintLayout, foodItem: FoodItem) {
            Log.d("onRecipeClickListener", "CALLED!")
            try {
                val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(foodItem)
                foodRecipesLayout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.d("onRecipeClickListener", e.toString())
                e.stackTrace
            }
        }

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: Resource<FoodItemResponse>?,
            database: List<RecipesEntity>?
        ) {
            if(apiResponse is Resource.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if(apiResponse is Resource.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if(apiResponse is Resource.Success) {
                imageView.visibility = View.INVISIBLE
            }

        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: Resource<FoodItemResponse>?,
            database: List<RecipesEntity>?
        ) {
            if(apiResponse is Resource.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
            } else if(apiResponse is Resource.Loading) {
                textView.visibility = View.INVISIBLE
            } else if(apiResponse is Resource.Success) {
                textView.visibility = View.INVISIBLE
            }

        }
    }

}