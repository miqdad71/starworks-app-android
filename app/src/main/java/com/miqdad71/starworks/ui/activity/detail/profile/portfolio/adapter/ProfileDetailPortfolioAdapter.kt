package com.miqdad71.starworks.ui.activity.detail.profile.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.portfolio.PortfolioModel
import com.miqdad71.starworks.databinding.ItemEngineerPortofolioBinding

class ProfileDetailPortfolioAdapter : RecyclerView.Adapter<ProfileDetailPortfolioAdapter.RecyclerViewHolder>() {
    private lateinit var binding: ItemEngineerPortofolioBinding
    private var items = mutableListOf<PortfolioModel>()

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binding(portfolio: PortfolioModel) {

            binding.portfolio = portfolio

            if (portfolio.pr_type == "aplikasi mobile") {
                binding.projectType = "Mobile"
            } else {
                binding.projectType = "Web"
            }

            /*if (portfolio.pr_image != null) {
                binding.imageUrl = BASE_URL_IMAGE + portfolio.pr_image
            } else {
                binding.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }*/

            binding.executePendingBindings()
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

    fun addList(list: List<PortfolioModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}