package com.myfridge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfridge.R
import com.myfridge.adapter.ProductsAdapter
import com.myfridge.storage.entity.Category
import com.myfridge.storage.entity.Product
import com.myfridge.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_tab_content.*
import timber.log.Timber
import java.util.stream.Collectors

class TabContentFragment : Fragment(R.layout.fragment_tab_content) {

    private val productsViewModel: ProductViewModel by viewModels()
    private lateinit var productCategory: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val category = getString(ARG_OBJECT)
            productCategory = category.toString()

            Timber.d("category:$category")
            subscribeToProducts()
        }
    }
    private fun subscribeToProducts() {
        val productsAdapter = ProductsAdapter(listOf())
        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        productsRecyclerView.adapter = productsAdapter

        productsViewModel.products.observe(viewLifecycleOwner, { list ->
            Timber.d("list:_$list")

           val l =  list.stream()
                .filter { it.category == productCategory }
                .collect(Collectors.toList())
                .toList()

            productsAdapter.setData(l)
        })

    }
    companion object {
        private const val ARG_OBJECT = "object"
    }
}
