package com.miqdad71.starworks.ui.adapter.skill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.databinding.ItemSkillBinding

class ProfileSkillAdapter : RecyclerView.Adapter<ProfileSkillAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemSkillBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<SkillModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(skill: SkillModel) {
            binding.skill = skill
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(skill)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_skill, parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: SkillModel)
    }

    fun addList(list: List<SkillModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}