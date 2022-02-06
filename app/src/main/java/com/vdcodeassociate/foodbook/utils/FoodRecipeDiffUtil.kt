package com.vdcodeassociate.foodbook.utils

import androidx.recyclerview.widget.DiffUtil
import com.vdcodeassociate.foodbook.models.FoodItem

class FoodRecipeDiffUtil(
    private val oldList: List<FoodItem>,
    private val newList: List<FoodItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}