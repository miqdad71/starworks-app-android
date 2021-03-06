package com.miqdad71.starworks.ui.adapter.company.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListHireCompanyBinding
import com.miqdad71.starworks.data.model.project.ProjectModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ItemListProjectCompanyBinding

class CompanyProjectAdapter : RecyclerView.Adapter<CompanyProjectAdapter.ProjectHolder>() {

    private var items = mutableListOf<ProjectModel>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<ProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListProjectCompanyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_project_company,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.cvProject.setOnClickListener {
            onItemClickCallback.onItemClick(item)
        }
        holder.binding.tvProjectName.text = item.pjProjectName
        holder.binding.tvDescription.text = item.pjDescription
        holder.binding.tvDeadline.text = item.pjDeadline!!.split('T')[0]
        Glide.with(holder.itemView.context).load(ApiClient.BASE_URL_IMAGE + item.pjImage).placeholder(R.drawable.ic_backround_user).into(holder.binding.ivImageProject)

    }

    interface OnItemClickCallback {
        fun onItemClick(data: ProjectModel)
    }

    override fun getItemCount(): Int = items.size
}