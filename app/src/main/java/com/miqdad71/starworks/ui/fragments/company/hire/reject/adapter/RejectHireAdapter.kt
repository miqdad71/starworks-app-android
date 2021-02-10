package com.miqdad71.starworks.ui.fragments.company.hire.reject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND
import com.miqdad71.starworks.databinding.ItemHireCompanyBinding
import com.miqdad71.starworks.util.Utils.Companion.currencyFormat

class RejectHireAdapter : RecyclerView.Adapter<RejectHireAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemHireCompanyBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<HireModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(hire: HireModel) {
            bind.hire = hire

            if (hire.pjImage != null) {
                bind.imageUrl = BASE_URL_IMAGE + hire.pjImage
            } else {
                bind.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

            if (hire.hrDateConfirm != null) {
                bind.tvConfirmDate.setTextColor(itemView.context.resources.getColor(R.color.red, itemView.context.theme))
                bind.tvConfirmDate.visibility = View.VISIBLE
                bind.date = "- reject at ${hire.hrDateConfirm}"
            } else {
                bind.tvConfirmDate.visibility = View.GONE
            }

            bind.price = "Rp. ${currencyFormat(hire.hrPrice.toString())} |"

            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(hire)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hire_company,
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
        fun onItemClick(data: HireModel)
    }

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}