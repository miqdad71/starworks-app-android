package com.miq71.starworks.activity.skill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miq71.starworks.R
import com.miq71.starworks.databinding.ItemSkillBinding
import com.miq71.starworks.model.skill.SkillModel

class ProfileSkillAdapter : RecyclerView.Adapter<ProfileSkillAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemSkillBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<SkillModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(skill: SkillModel) {
            bind.skill = skill
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(skill)
            }
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

    interface OnItemClickCallback {
        fun onItemClick(data: SkillModel)
    }

    fun addList(list: List<SkillModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}