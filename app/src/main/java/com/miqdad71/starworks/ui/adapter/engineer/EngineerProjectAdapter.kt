package com.miqdad71.starworks.ui.adapter.engineer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListHireCompanyBinding
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ItemListHireBinding
import java.util.*

class EngineerProjectAdapter : RecyclerView.Adapter<EngineerProjectAdapter.ProjectHolder>() {

    private var items = mutableListOf<HireModel>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListHireBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_hire,
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
        holder.binding.tvListStatus.text = item.hrStatus!!.toUpperCase(Locale.ROOT)
        Glide.with(holder.itemView.context).load(ApiClient.BASE_URL_IMAGE + item.pjImage).placeholder(R.drawable.ic_backround_user).into(holder.binding.imgListSearchProject)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: HireModel)
    }

    override fun getItemCount(): Int = items.size
}