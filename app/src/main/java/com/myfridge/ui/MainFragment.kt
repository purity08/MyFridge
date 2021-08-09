package com.myfridge.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.myfridge.R
import com.myfridge.adapter.PageAdapter
import com.myfridge.storage.entity.Category
import com.myfridge.storage.entity.Product
import com.myfridge.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var viewPager: ViewPager2
    private var categoriesList: ArrayList<Category> = arrayListOf()
    private var productsList: ArrayList<Product> = arrayListOf()

    private val productsViewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("products_on_created$productsList")

        //subscribeToProducts()
        initializeCategories()

        fab_add_product.setOnClickListener {
            var id = 0
            productsViewModel.insertProduct(
                Product(
                    id = ++id,
                    name = "apple $id",
                    category = requireContext().getString(R.string.fruits)
                )
            )
        }
    }

    private fun initializeCategories() {
        MainScope().async {
            loadCategories()
        }.invokeOnCompletion {
            setupTabs()
        }

    }

    private fun loadCategories() {
        var id = 0
        Timber.d("add_categories")
        categoriesList.apply {
            add(Category(id++, requireContext().getString(R.string.fruits)))
            add(Category(id++, requireContext().getString(R.string.vegetables)))
            add(Category(id++, requireContext().getString(R.string.milk_products)))
            add(Category(id++, requireContext().getString(R.string.meat)))
            add(Category(id, requireContext().getString(R.string.other)))
        }
    }

    private fun setupTabs() {
        pageAdapter = PageAdapter(this, categoriesList)
        viewPager = pager
        viewPager.adapter = pageAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categoriesList[position].name
        }.attach()
    }

}
