package com.miqdad71.starworks.tryweek7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListSearchEngineerBinding

class ListEngineerAdapter : RecyclerView.Adapter<ListEngineerAdapter.ProjectHolder>() {

    private var items = mutableListOf<ListEngineerModel>()

    fun addList(list: List<ListEngineerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListSearchEngineerBinding) : RecyclerView.ViewHolder(binding.root)

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