package com.miqdad71.starworks.ui.adapter.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.experience.ExperienceModel
import com.miqdad71.starworks.databinding.ItemEngineerExperienceBinding
import com.miqdad71.starworks.util.Utils

class ExperienceEngineerAdapter: RecyclerView.Adapter<ExperienceEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemEngineerExperienceBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<ExperienceModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(exp: ExperienceModel) {
            binding.experience = exp
            binding.rangeDate = Utils.rangeMonth(
                exp.ex_start!!.split('T')[0],
                exp.ex_end!!.split('T')[0]
            ) + " month"
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(exp)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_engineer_experience, parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: ExperienceModel)
    }

    fun addList(list: List<ExperienceModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}