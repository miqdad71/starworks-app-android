package com.miqdad71.starworks.ui.adapter.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListSearchEngineerBinding
import com.miqdad71.starworks.data.model.company.SearchEngineerModel

class SearchCompanyAdapter : RecyclerView.Adapter<SearchCompanyAdapter.ProjectHolder>() {

    private var items = mutableListOf<SearchEngineerModel>()

    fun addList(list: List<SearchEngineerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListSearchEngineerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_search_engineer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.tvListSearchEngName.text = item.acName
        holder.binding.tvListSearchEngJob.text = item.enJobType

    }

    override fun getItemCount(): Int = items.size
}