package com.miqdad71.starworks.ui.adapter.company.hire

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListHireCompanyBinding
import com.miqdad71.starworks.data.model.hire.HireModel
import java.util.*

class CompanyHireAdapter : RecyclerView.Adapter<CompanyHireAdapter.ProjectHolder>() {

    private var items = mutableListOf<HireModel>()

    fun addList(list: List<HireModel>) {
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
        holder.binding.tvListStatus.text = item.hrStatus!!.toUpperCase(Locale.ROOT)
        holder.binding.tvListEngineer.text = item.acName
        holder.binding.tvListSearchProjectName.text = item.pjProjectName
        holder.binding.tvListSearchProjectDesc.text = item.pjDescription

    }
    override fun getItemCount(): Int = items.size
}