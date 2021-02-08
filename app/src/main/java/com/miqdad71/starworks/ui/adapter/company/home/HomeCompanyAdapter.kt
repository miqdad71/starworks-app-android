package com.miqdad71.starworks.ui.adapter.company.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_PROFILE
import com.miqdad71.starworks.databinding.ItemListWebDevBinding

class HomeCompanyAdapter : RecyclerView.Adapter<HomeCompanyAdapter.ProjectHolder>() {

    private var items = mutableListOf<EngineerModel>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<EngineerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemListWebDevBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_web_dev,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.lnIdentity.setOnClickListener {
            onItemClickCallback.onItemClick(item)
        }
        holder.binding.tvName.text = item.acName
        holder.binding.tvJobTitle.text = item.enJobType
        holder.binding.tvDomicile.text = item.enDomicile
        if (item.enProfile != null){
            Glide.with(holder.binding.root).load(BASE_URL_IMAGE + item.enProfile)
                .placeholder(R.drawable.ic_backround_user).into(holder.binding.ivImageProfile)
        } else {
            Glide.with(holder.binding.root).load(BASE_URL_IMAGE_DEFAULT_PROFILE)
                .placeholder(R.drawable.ic_backround_user).into(holder.binding.ivImageProfile)
        }


    }

    interface OnItemClickCallback {
        fun onItemClick(data: EngineerModel)
    }

    override fun getItemCount(): Int = items.size
}


