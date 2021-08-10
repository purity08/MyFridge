package com.myfridge.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfridge.R
import com.myfridge.adapter.ProductsAdapter
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

    private fun createConfirmDialog(product: Product): AlertDialog? {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage("Delete '${product.name}'?")
            ?.setPositiveButton(R.string.dialog_ok) { _, _ ->
                productsViewModel.removeProduct(product)
            }
            ?.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                product.count = 1
                dialog.cancel()
            }
        return builder?.create()
    }

    private fun subscribeToProducts() {
        val onClickAddProduct: (Product) -> Unit = {
            productsViewModel.updateProduct(it)
        }

        val onClickRemoveProduct: (Product) -> Unit = { product ->
            if (product.count == 0) {
                createConfirmDialog(product)?.show()
            } else {
                productsViewModel.updateProduct(product)
            }
        }

        val productsAdapter = ProductsAdapter(listOf(), onClickAddProduct, onClickRemoveProduct)
        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        productsRecyclerView.adapter = productsAdapter

        productsViewModel.products.observe(viewLifecycleOwner, { list ->
            Timber.d("list:_$list")

            val l = list.stream()
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
