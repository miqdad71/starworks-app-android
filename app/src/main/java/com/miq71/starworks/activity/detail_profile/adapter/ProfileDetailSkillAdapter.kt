package com.miq71.starworks.activity.detail_profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miq71.starworks.R
import com.miq71.starworks.databinding.ItemSkillBinding
import com.miq71.starworks.model.skill.SkillModel

class ProfileDetailSkillAdapter :
    RecyclerView.Adapter<ProfileDetailSkillAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemSkillBinding
    private var items = mutableListOf<SkillModel>()

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(skill: SkillModel) {
            bind.skill = skill
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_skill,
            parent,
            false
        )
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(list: List<SkillModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}