package com.myfridge.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfridge.ui.TabContentFragment
import timber.log.Timber

class PageAdapter(fragment: Fragment, private val productsList: List<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {

        val fragment = TabContentFragment()
        Timber.d("products_list:$productsList")
        fragment.arguments = Bundle().apply {
           //putSerializable(ARG_OBJECT, productsList as Serializable)
            putString(ARG_OBJECT, productsList[position])
        }
        return fragment
    }

    companion object {
        private const val ARG_OBJECT = "object"
    }
}