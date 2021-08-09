package com.myfridge.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfridge.storage.entity.Category
import com.myfridge.ui.TabContentFragment

class PageAdapter(fragment: Fragment, private val categoriesList: ArrayList<Category>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {

        val fragment = TabContentFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT, categoriesList[position].name)
        }
        return fragment
    }

    companion object {
        private const val ARG_OBJECT = "object"
    }
}