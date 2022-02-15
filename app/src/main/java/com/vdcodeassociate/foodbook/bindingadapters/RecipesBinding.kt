package com.vdcodeassociate.foodbook.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vdcodeassociate.foodbook.models.FoodItemResponse
import com.vdcodeassociate.foodbook.data.room.RecipesEntity
import com.vdcodeassociate.foodbook.utils.Resource

class RecipesBinding {

    companion object {

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