package com.vdcodeassociate.foodbook.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.models.FoodItem
import com.vdcodeassociate.foodbook.ui.fragments.mainactivity.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class FoodRecipeBinding {

    companion object {

        // functions for binding recycler per item clicked
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, item: FoodItem) {
            Log.d("onRecipeClickListener","Called!")
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(item)
                    recipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        // functions for binding recycler view adapter
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMin")
        @JvmStatic
        fun setNumberOfMin(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if(vegan){
                when(view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green_color))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green_color))

                    }
                }
            }
        }

        @BindingAdapter("parseHTML")
        @JvmStatic
        fun parseHTML(textView: TextView, description: String?) {
            if(description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

    }

}