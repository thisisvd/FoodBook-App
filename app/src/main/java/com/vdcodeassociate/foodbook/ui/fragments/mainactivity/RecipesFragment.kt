package com.vdcodeassociate.foodbook.ui.fragments.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vdcodeassociate.foodbook.R
import com.vdcodeassociate.foodbook.adapters.FoodRecipeAdapter
import com.vdcodeassociate.foodbook.databinding.FragmentRecipesBinding
import com.vdcodeassociate.foodbook.ui.viewmodels.FoodRecipeViewModel
import com.vdcodeassociate.foodbook.ui.viewmodels.MainViewModel
import com.vdcodeassociate.foodbook.utils.NetworkListener
import com.vdcodeassociate.foodbook.utils.Resource
import com.vdcodeassociate.foodbook.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

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

    // network listener
    private lateinit var networkListener: NetworkListener

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

            // setting menu in fragment
            setHasOptionsMenu(true)

            // setUp recycler
            setUpRecyclerView()

            // saving the current back-online value from datastore
            recipeViewModel.readBackOnline.observe(viewLifecycleOwner) {
                recipeViewModel.backOnline = it
            }

            // network listener init and collecting values from mutableStateFlow
            lifecycleScope.launch {
                networkListener = NetworkListener()
                networkListener.checkNetworkAvailability(requireContext())
                    .collect { status ->
                        Log.d("NetworkListener",status.toString())
                        recipeViewModel.networkStatus = status
                        recipeViewModel.showNetworkStatus()
                        readDataBase()
                    }
            }

            // fab listener
            floatingActionButton.setOnClickListener {

                // only do if network is available
                if (recipeViewModel.networkStatus) {
                    findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
                } else {
                    recipeViewModel.showNetworkStatus()
                }

            }

        }
        return binding.root
    }

    // on create menu items
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)

        // menu item configs
        val search = menu.findItem(R.id.menuSearch)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    // query for api data
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchAPIData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    // read data from database
    private fun readDataBase() {

        // read particular recipe
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d(TAG, "readDatabase called!")
                    recyclerAdapter.setData(database[0].foodRecipes)
                    hideShimmerEffect()
                } else {
                    requestAPIData()
                }
            }
        }
    }

    // viewModel main data request
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
                    is Resource.Loading -> {
                        showShimmerEffect()
                    }
                }
            }

        }
    }

    // viewModel SEARCH ITEM request
    private fun searchAPIData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipeItem(recipeViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { recyclerAdapter.setData(it) }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Log.d(TAG, "Error occurred while loading data! ${response.message.toString()}")
                }
                is Resource.Loading -> {
                    showShimmerEffect()
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
        binding.recyclerView.showShimmer()
    }

    // hide shimmer effect
    private fun hideShimmerEffect(){
        binding.recyclerView.hideShimmer()
    }

}