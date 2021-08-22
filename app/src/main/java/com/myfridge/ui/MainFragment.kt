package com.myfridge.ui


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.myfridge.R
import com.myfridge.adapter.PageAdapter
import com.myfridge.storage.entity.Category
import com.myfridge.storage.entity.Product
import com.myfridge.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.dialog_add_checklist.view.*
import kotlinx.android.synthetic.main.dialog_add_product.*
import kotlinx.android.synthetic.main.dialog_add_product.view.*
import kotlinx.android.synthetic.main.dialog_add_product.view.dialog_cancel_button
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var viewPager: ViewPager2
    private var categoriesList: ArrayList<Category> = arrayListOf()
    private var pickedCategoryId = 0

    private val productsViewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeCategories()

        fab_add_product.setOnClickListener {
            createAddProductDialog()
        }
    }

    private fun createAddProductDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_product, null)
        val builder = MaterialAlertDialogBuilder(requireContext()).setView(dialogView)
        builder.background = ColorDrawable(Color.TRANSPARENT)

        val dialog = builder.create()
        dialog.window?.setDimAmount(0.6F)
        dialog.show()

        val radioGroupView = dialogView.radio_group
        radioGroupView.orientation = RadioGroup.VERTICAL

        var i = 0
        categoriesList.stream()
            .forEach {
                val button = RadioButton(requireContext())
                button.text = it.name
                button.id = i++
                radioGroupView.addView(button)
            }

        radioGroupView.setOnCheckedChangeListener { radioGroup, checkedId ->
            Timber.d("checkedId: $checkedId")
            pickedCategoryId = checkedId

            dialogView.productCategoryEditText.setText(categoriesList[pickedCategoryId].name)

            dialogView.productCategoryEditText.visibility = View.VISIBLE
            dialogView.pick_category_button.visibility = View.VISIBLE
            radioGroupView.visibility = View.GONE
        }

        dialogView.pick_category_button.setOnClickListener {
            radioGroupView.visibility = View.VISIBLE
            dialogView.pick_category_button.visibility = View.GONE
        }

        dialogView.dialog_cancel_button.setOnClickListener {
            dialog.cancel()
        }

        dialogView.dialog_ok_button.setOnClickListener {
            val name = dialogView.productNameEditText.text.toString()
            val category = dialogView.productCategoryEditText.text.toString()

            if (name.isNotEmpty() && category.isNotEmpty()) {
                productsViewModel.insertProduct(
                    Product(
                        id = "id_$name",
                        name = name,
                        count = 1,
                        category = category
                    )
                )
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Fill all fields!", Toast.LENGTH_SHORT).show()
            }
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
