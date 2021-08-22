package com.myfridge.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfridge.R
import com.myfridge.adapter.ExpandedCheckListAdapter
import kotlinx.android.synthetic.main.dialog_add_checklist.*
import kotlinx.android.synthetic.main.dialog_add_checklist.view.*
import kotlinx.android.synthetic.main.fragment_check_list.*
import timber.log.Timber

class CheckListFragment : Fragment(R.layout.fragment_check_list) {

    private lateinit var checkLists: HashMap<String, List<String>>
    private lateinit var checkListAdapter: ExpandedCheckListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        checkLists = HashMap()

        val expandableListTitle = checkLists.keys

        checkListAdapter = ExpandedCheckListAdapter(
            requireContext(),
            checkLists,
            expandableListTitle.toList()
        )

        expanded_checklist.setAdapter(checkListAdapter)

        expanded_checklist.setOnGroupClickListener { expandableListView, view, i, l ->
            false
        }

        expanded_checklist.setOnChildClickListener { expandableListView, view, i, i2, l ->
            false
        }

        expanded_checklist.setOnGroupExpandListener {

        }

        fab_add_checklist.setOnClickListener {
            createAddCheckListDialog()
        }
    }

    private fun newCheckList(title: String, items: List<String>){
        checkLists[title] = items
    }

    private fun createAddCheckListDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_checklist, null)
        val builder = MaterialAlertDialogBuilder(requireContext()).setView(dialogView)
        builder.background = ColorDrawable(Color.TRANSPARENT)

        val dialog = builder.create()
        dialog.window?.setDimAmount(0.6F)
        dialog.show()

        dialogView.dialog_checklist_ok_button.setOnClickListener {
            val checkListTitle = dialogView.checklist_title_editText.text.toString()
            val checkListProducts = dialogView.checkList_product_editText.text.toString().split("\n")
            Timber.d(checkListProducts.toString())

            val productsList = arrayListOf<String>()
            productsList.add(0,dialogView.checkList_product_editText.text.toString())
            newCheckList(checkListTitle, productsList)
            checkListAdapter.setData(checkLists)
            dialog.dismiss()
        }

        dialogView.dialog_checklist_cancel_button.setOnClickListener {
            dialog.cancel()
        }

    }
}