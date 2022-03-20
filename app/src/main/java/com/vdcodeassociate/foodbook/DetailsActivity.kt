package com.vdcodeassociate.foodbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.vdcodeassociate.foodbook.adapters.PagerAdapterClass
import com.vdcodeassociate.foodbook.databinding.ActivityDetailsBinding
import com.vdcodeassociate.foodbook.ui.fragments.detailsactivity.IngredientsFragment
import com.vdcodeassociate.foodbook.ui.fragments.detailsactivity.InstructionsFragment
import com.vdcodeassociate.foodbook.ui.fragments.detailsactivity.OverviewFragment

class DetailsActivity : AppCompatActivity() {

    // TAG
    private final val TAG = "DetailsActivity"

    // viewBinding
    private lateinit var binding: ActivityDetailsBinding

    // arguments
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            // toolbar setup
            setSupportActionBar(binding.toolbar)
            toolbar.setTitleTextColor(ContextCompat.getColor(this@DetailsActivity, R.color.white))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // fragments
            val fragments = ArrayList<Fragment>()
            fragments.add(OverviewFragment())
            fragments.add(IngredientsFragment())
            fragments.add(InstructionsFragment())

            // titles
            val titles = ArrayList<String>()
            titles.add("Overview")
            titles.add("Ingredients")
            titles.add("Instructions")

            // bundle
            val bundle = Bundle()
            bundle.putSerializable("recipeBundle", args.result)

            // adapter
            val adapter = PagerAdapterClass(
                bundle,
                fragments,
                titles,
                supportFragmentManager
            )

            // setting the adapter
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}