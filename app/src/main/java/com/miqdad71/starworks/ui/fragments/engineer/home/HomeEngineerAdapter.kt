package com.miqdad71.starworks.ui.fragments.engineer.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemListWebDevBinding
import com.miqdad71.starworks.data.model.engineer.EngineerModel

class HomeEngineerAdapter : RecyclerView.Adapter<HomeEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemListWebDevBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<EngineerModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(en: EngineerModel) {
            binding.engineer = en
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(en)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list_web_dev,
            parent,
            false
        )
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: EngineerModel)
    }

    fun addList(list: List<EngineerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}