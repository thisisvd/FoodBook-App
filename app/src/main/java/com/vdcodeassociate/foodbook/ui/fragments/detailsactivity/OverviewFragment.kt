package com.vdcodeassociate.foodbook.ui.fragments.detailsactivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.databinding.FragmentOverviewBinding
import com.vdcodeassociate.foodbook.models.FoodItem
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    // viewBinding
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.apply {

            // args
            val args = arguments
            val bundle: FoodItem? = args?.getSerializable("recipeBundle") as FoodItem

            // loading data
            loadOverviewData(bundle)

        }
        return binding.root
    }

    // load food data
    private fun loadOverviewData(item: FoodItem?) {
        binding.apply {
            if (item != null) {

                // load texts
                imageView.load(item.image)
                titleTV.text = item.title
                likeTV.text = item.aggregateLikes.toString()
                timeTV.text = item.readyInMinutes.toString()
                item.summary.let {
                    val summary = Jsoup.parse(it).text()
                    summaryTV.text = summary
                }

                // bottom checks observers
                if (item.vegetarian) {
                    vegetarianIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    vegetarianTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if (item.vegan) {
                    veganIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    veganTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if (item.glutenFree) {
                    glutenFreeIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    glutenFreeTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if (item.dairyFree) {
                    dairyFreeIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    dairyFreeTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if (item.glutenFree) {
                    glutenFreeIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    glutenFreeTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

                if (item.cheap) {
                    cheapIV.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    cheapTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }

            }
        }
    }

    // on destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}