package com.miqdad71.starworks.ui.activity.detail.profile.experience.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.experience.ExperienceModel
import com.miqdad71.starworks.databinding.ItemEngineerExperienceBinding
import com.miqdad71.starworks.util.Utils.Companion.rangeMonth

class ProfileDetailExperienceAdapter : RecyclerView.Adapter<ProfileDetailExperienceAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemEngineerExperienceBinding
    private var items = mutableListOf<ExperienceModel>()

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(experience: ExperienceModel) {
            binding.experience = experience
            binding.rangeDate = rangeMonth(
                experience.ex_start!!.split('T')[0],
                experience.ex_end!!.split('T')[0]
            ) + " month"
            binding.executePendingBindings()
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

    fun addList(list: List<ExperienceModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}