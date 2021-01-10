package com.miqdad71.starworks.ui.adapter.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.project.ProjectModel
import com.miqdad71.starworks.data.model.project.ProjectResponse
import com.miqdad71.starworks.databinding.ItemProjectSpinnerBinding

class ProjectSpinnerAdapter(private var context: Context) : BaseAdapter() {
    private lateinit var bind: ItemProjectSpinnerBinding
    private var items = mutableListOf<ProjectModel>()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): Any {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            bind = DataBindingUtil.inflate(inflater, R.layout.item_project_spinner, parent, false)
        }

        bind.project = items[i]

        return bind.root
    }

    fun addList(list: List<ProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}