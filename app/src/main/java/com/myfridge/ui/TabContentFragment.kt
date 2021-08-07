package com.myfridge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfridge.R
import com.myfridge.storage.entity.Category
import kotlinx.android.synthetic.main.fragment_tab_content.*
import timber.log.Timber

class TabContentFragment : Fragment(R.layout.fragment_tab_content) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            Timber.d("contains_key")
            //tabText.text = (getSerializable(ARG_OBJECT) as (List<*>)).toString()
            tabText.text = getString(ARG_OBJECT)
        }
    }

    companion object {
        private const val ARG_OBJECT = "object"
    }
}
