package com.miqdad71.starworks.ui.adapter.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListHireCompanyBinding
import com.miqdad71.starworks.data.model.engineer.HireProjectModel

class CompanyProjectAdapter : RecyclerView.Adapter<CompanyProjectAdapter.ProjectHolder>() {

    private var items = mutableListOf<HireProjectModel>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<HireProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListHireCompanyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_hire_company,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.itemHireEngineer.setOnClickListener {
            onItemClickCallback.onItemClick(item)
        }
        holder.binding.tvListSearchProjectName.text = item.pjProjectName
        holder.binding.tvListSearchProjectDesc.text = item.pjDescription
        holder.binding.tvPrice.text = item.hrPrice


    }

    interface OnItemClickCallback {
        fun onItemClick(data: HireProjectModel)
    }

    override fun getItemCount(): Int = items.size
}