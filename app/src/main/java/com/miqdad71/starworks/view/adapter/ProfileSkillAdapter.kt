package com.miqdad71.starworks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ItemSkillBinding
import com.miqdad71.starworks.view.model.SkillModel

class ProfileSkillAdapter(private val model: ArrayList<SkillModel>) : RecyclerView.Adapter<ProfileSkillAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemSkillBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(skill: SkillModel) {
            bind.skill = skill
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(skill)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_skill, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: SkillModel)
    }
}