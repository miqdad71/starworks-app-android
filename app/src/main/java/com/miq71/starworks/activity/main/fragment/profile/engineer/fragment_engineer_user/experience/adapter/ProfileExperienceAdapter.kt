package com.miq71.starworks.activity.main.fragment.profile.engineer.fragment_engineer_user.experience.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miq71.starworks.R
import com.miq71.starworks.databinding.ItemExperienceBinding
import com.miq71.starworks.model.experience.ExperienceModel
import com.miq71.starworks.util.Utils

class ProfileExperienceAdapter : RecyclerView.Adapter<ProfileExperienceAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemExperienceBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<ExperienceModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(exp: ExperienceModel) {
            bind.experience = exp
            bind.rangeDate = Utils.rangeMonth(
                exp.ex_start!!.split('T')[0],
                exp.ex_end!!.split('T')[0]
            ) + " month"
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(exp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_experience, parent, false)
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
        fun onItemClick(data: ExperienceModel)
    }

    fun addList(list: List<ExperienceModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}