package com.myfridge.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfridge.ui.TabContentFragment

class PageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {

        val fragment = TabContentFragment()
        fragment.arguments = Bundle().apply {
           //putSerializable(ARG_OBJECT, productsList as Serializable)
            //putString(ARG_OBJECT, productsList[position])
        }
        return fragment
    }

    companion object {
        private const val ARG_OBJECT = "object"
    }
}