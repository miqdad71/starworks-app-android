package com.miqdad71.starworks.ui.fragments.company.hire.reject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND
import com.miqdad71.starworks.databinding.ItemHireCompanyBinding
import com.miqdad71.starworks.util.Utils.Companion.currencyFormat

class RejectHireAdapter : RecyclerView.Adapter<RejectHireAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemHireCompanyBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<HireModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(hire: HireModel) {
            binding.hire = hire

            if (hire.pjImage != null){
            Glide.with(binding.root).load(BASE_URL_IMAGE + hire.pjImage)
                .placeholder(R.drawable.ic_backround_user).into(binding.ivImageProject)
            } else {
            Glide.with(binding.root).load(ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE)
                .placeholder(R.drawable.ic_backround_user).into(binding.ivImageProject)
            }

            if (hire.hrDateConfirm != null) {
                binding.tvConfirmDate.setTextColor(itemView.context.resources.getColor(R.color.red, itemView.context.theme))
                binding.tvConfirmDate.visibility = View.VISIBLE
                binding.date = "- reject at ${hire.hrDateConfirm}"
            } else {
                binding.tvConfirmDate.visibility = View.GONE
            }

            binding.price = "Rp. ${currencyFormat(hire.hrPrice.toString())} |"

            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(hire)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hire_company,
            parent,
            false
        )
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
        fun onItemClick(data: HireModel)
    }

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}