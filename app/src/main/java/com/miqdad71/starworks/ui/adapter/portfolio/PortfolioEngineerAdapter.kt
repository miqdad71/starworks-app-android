package com.miqdad71.starworks.ui.adapter.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.portfolio.PortfolioModel
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.databinding.ItemEngineerPortofolioBinding

class PortfolioEngineerAdapter: RecyclerView.Adapter<PortfolioEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemEngineerPortofolioBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<PortfolioModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(portfolio: PortfolioModel) {

            binding.tvAplicationName.text = portfolio.pr_app

            if (portfolio.pr_type == "aplikasi mobile") {
                binding.tvTypePortfolio.text = "Mobile"
            } else {
                binding.tvTypePortfolio.text = "Web"
            }

            if (portfolio.pr_image != null) {
                Glide.with(itemView.context).load(BASE_URL_IMAGE + portfolio.pr_image).into(binding.ivImageProject)
            }

            binding.executePendingBindings()

            binding.cvProject.setOnClickListener {
                onItemClickCallback.onItemClick(portfolio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_engineer_portofolio, parent, false)
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
        fun onItemClick(data: PortfolioModel)
    }

    fun addList(list: List<PortfolioModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}