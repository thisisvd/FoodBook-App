package com.vdcodeassociate.foodbook.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapterClass(
    private val bundle: Bundle,
    private val fragment: ArrayList<Fragment>,
    private val title: ArrayList<String>,
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        fragment[position].arguments = bundle
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}