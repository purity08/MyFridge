package com.myfridge.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.myfridge.R
import com.myfridge.adapter.PageAdapter
import com.myfridge.storage.entity.Category
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var viewPager: ViewPager2
    private var categoriesList: ArrayList<Category> = arrayListOf()
    private var productsList: ArrayList<String> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories()
        setupTabs()
    }

    private fun loadCategories() {
        var id = 0
        categoriesList.apply {
            add(Category(id++, requireContext().getString(R.string.fruits)))
            add(Category(id++, requireContext().getString(R.string.vegetables)))
            add(Category(id++, requireContext().getString(R.string.milk_products)))
            add(Category(id++, requireContext().getString(R.string.meat)))
            add(Category(id, requireContext().getString(R.string.other)))
        }
        productsList.apply {
            add(requireContext().getString(R.string.fruits))
            add(requireContext().getString(R.string.vegetables))
            add(requireContext().getString(R.string.milk_products))
            add(requireContext().getString(R.string.meat))
            add(requireContext().getString(R.string.other))
        }
    }
    private fun setupTabs() {
        pageAdapter = PageAdapter(this, productsList)
        viewPager = pager
        viewPager.adapter = pageAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categoriesList[position].name
        }.attach()
    }

}
