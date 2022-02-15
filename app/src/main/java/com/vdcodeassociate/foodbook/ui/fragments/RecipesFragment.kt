package com.vdcodeassociate.foodbook.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.adapters.FoodRecipeAdapter
import com.vdcodeassociate.foodbook.constants.Constants.Companion.API_KEY
import com.vdcodeassociate.foodbook.databinding.FragmentRecipesBinding
import com.vdcodeassociate.foodbook.ui.viewmodels.FoodRecipeViewModel
import com.vdcodeassociate.foodbook.ui.viewmodels.MainViewModel
import com.vdcodeassociate.foodbook.utils.Resource
import com.vdcodeassociate.foodbook.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    // TAG
    private val TAG = "RecipesFragment"

    // nag-args
    private val args by navArgs<RecipesFragmentArgs>()

    // view Binding
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    // viewModel init
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: FoodRecipeViewModel

    // recycler adapter
    private val recyclerAdapter by lazy { FoodRecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewModel int
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(FoodRecipeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.apply {

            // setUp recycler
            setUpRecyclerView()

            // viewModel init
            readDataBase()

            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            }

        }
        return binding.root
    }

    // read data from database
    private fun readDataBase() {
       lifecycleScope.launch {
           mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
               if(database.isNotEmpty() && !args.backFromBottomSheet){
                   Log.d(TAG,"readDatabase called!")
                   recyclerAdapter.setData(database[0].foodRecipes)
                   hideShimmerEffect()
               } else {
                   requestAPIData()
               }
           }
       }
    }

    // viewModel request
    private fun requestAPIData() {
        mainViewModel.apply {
            Log.d(TAG,"recipesAPIData called!")
            // requesting food - api data
            getFoodItem(recipeViewModel.applyQueries())

            // viewModel observer
            recipeResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideShimmerEffect()
                        response.data?.let { recyclerAdapter.setData(it) }
                    }
                    is Resource.Error -> {
                        hideShimmerEffect()
                        loadDataFromCache()
                        Log.d(TAG, "Error occurred while loading data! ${response.message}")
                    }
                    is Resource.Error -> {
                        showShimmerEffect()
                    }
                }
            }

        }
    }

    // if will get any error then load db only from cache
    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if(database.isNotEmpty()){
                    recyclerAdapter.setData(database[0].foodRecipes)
                }
            }
        }
    }

    // set up recycler view
    private fun setUpRecyclerView(){
        binding.apply {
            recyclerView.apply {
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                showShimmer()
            }
        }
    }

    // show shimmer effect
    private fun showShimmerEffect(){
        binding.recyclerView.hideShimmer()
    }

    // hide shimmer effect
    private fun hideShimmerEffect(){
        binding.recyclerView.hideShimmer()
    }

}