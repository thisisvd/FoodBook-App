package com.vdcodeassociate.foodbook.ui.fragments.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_DIET_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_MEAL_TYPE
import com.vdcodeassociate.foodbook.databinding.RecipesBottomSheetBinding
import com.vdcodeassociate.foodbook.ui.viewmodels.FoodRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {

    // view Binding
    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private lateinit var viewModel: FoodRecipeViewModel

    // bottom chip bar vars
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FoodRecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        binding.apply {

            // read existing saved values and update in UI
            viewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
                mealTypeChip = value.selectedMealType
                dietTypeChip = value.selectedDietType
                updateChipUI(value.selectedMealTypeId, mealTypeChipGroup)
                updateChipUI(value.selectedDietTypeId, dietTypeChipGroup)
            }

            mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
                val chip = group.findViewById<Chip>(checkedId)
                val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                mealTypeChip = selectedMealType
                mealTypeChipId = checkedId
            }

            dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
                val chip = group.findViewById<Chip>(checkedId)
                val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
                dietTypeChip = selectedDietType
                dietTypeChipId = checkedId
            }

            applyButton.setOnClickListener {
                viewModel.saveMealAndDietType(
                    mealTypeChip,
                    mealTypeChipId,
                    dietTypeChip,
                    dietTypeChipId
                )

                val bundle = bundleOf("backFromBottomSheet" to true)
                findNavController().navigate(
                    R.id.action_recipesBottomSheet_to_recipesFragment,
                    bundle
                )
            }

        }

        return binding.root
    }

    private fun updateChipUI(chipID: Int, chipGroup: ChipGroup) {
        if (chipID != 0) {
            try {
                chipGroup.findViewById<Chip>(chipID).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipeBottomSheet", e.message.toString())
            }
        }
    }

}