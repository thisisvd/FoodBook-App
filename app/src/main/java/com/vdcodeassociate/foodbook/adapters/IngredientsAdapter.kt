package com.vdcodeassociate.foodbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.databinding.IngredientsRowLayoutBinding
import com.vdcodeassociate.foodbook.models.ExtendedIngredient

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    // inner class
    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
//        holder.itemView.
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

}