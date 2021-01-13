package com.miqdad71.starworks.ui.adapter.company.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListWebDevBinding
import com.miqdad71.starworks.data.model.engineer.EngineerModel

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
        holder.binding.rlItemWeb.setOnClickListener {
            onItemClickCallback.onItemClick(item)
        }
        holder.binding.tvEngWeb.text = item.acName
        holder.binding.tvEngJob.text = item.enJobType

    }

    interface OnItemClickCallback {
        fun onItemClick(data: EngineerModel)
    }

    override fun getItemCount(): Int = items.size
}


