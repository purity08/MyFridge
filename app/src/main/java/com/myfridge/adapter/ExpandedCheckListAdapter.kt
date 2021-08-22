package com.myfridge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.collection.ArrayMap
import com.google.android.material.button.MaterialButton
import com.myfridge.R

class ExpandedCheckListAdapter(val context: Context, var checkList: HashMap<String, List<String>>, private var listTitle: List<String>) :
    BaseExpandableListAdapter() {

    fun setData(checkList: HashMap<String, List<String>>) {
        this.checkList = checkList
        this.listTitle = checkList.keys.toList()
        notifyDataSetChanged()
    }

    override fun getGroupCount(): Int {
        return checkList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return checkList[listTitle[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return listTitle[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return checkList[listTitle[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View? {
        var groupView = view
        if (groupView == null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            groupView = inflater.inflate(R.layout.item_group_view, null)
        }
        val groupTitle = getGroup(groupPosition) as String

        groupView?.findViewById<TextView>(R.id.group_textView)?.text =
            groupTitle

        return groupView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View {
        val childView: View?
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        childView = inflater.inflate(R.layout.item_group_child_view, null)

        val childDescription = getChild(groupPosition, childPosition) as String
        childView.findViewById<TextView>(R.id.group_child_textView).text =
            childDescription

        if (childPosition == 0 ) {
            childView?.findViewById<MaterialButton>(R.id.add_child)?.visibility = View.VISIBLE
        }

        childView?.findViewById<MaterialButton>(R.id.add_child)?.setOnClickListener {
            //chCount++
            notifyDataSetChanged()
        }

        return childView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

}