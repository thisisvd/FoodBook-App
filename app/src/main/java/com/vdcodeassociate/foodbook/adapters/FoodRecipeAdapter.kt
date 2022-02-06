package com.vdcodeassociate.foodbook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vdcodeassociate.foodbook.databinding.RecipesItemBinding
import com.vdcodeassociate.foodbook.models.FoodItem
import com.vdcodeassociate.foodbook.models.FoodItemResponse
import com.vdcodeassociate.foodbook.utils.FoodRecipeDiffUtil

class FoodRecipeAdapter: RecyclerView.Adapter<FoodRecipeAdapter.FoodRecipeViewHolder>() {

    // private list data
    private var foodRecipe = emptyList<FoodItem>()

    // inner class
    class FoodRecipeViewHolder(private val binding: RecipesItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodItem) {
            binding.foodItem = item
            binding.executePendingBindings()
        }

        // returning viewHolder function
        companion object {
            fun from(parent: ViewGroup): FoodRecipeViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesItemBinding.inflate(layoutInflater, parent, false)
                return FoodRecipeViewHolder(binding)
            }
        }
    }

    // member functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecipeViewHolder {
        return FoodRecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodRecipeViewHolder, position: Int) {
        val currentItem = foodRecipe[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return foodRecipe.size
    }

    // setting the data to private list
    fun setData(newFoodItem: FoodItemResponse){
        val recipesDiffUtils = FoodRecipeDiffUtil(foodRecipe, newFoodItem.results)
        val diffUtilResults = DiffUtil.calculateDiff(recipesDiffUtils)
        foodRecipe = newFoodItem.results
        diffUtilResults.dispatchUpdatesTo(this)
    }

}