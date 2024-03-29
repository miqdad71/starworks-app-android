package com.miq71.starworks.activity.main.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.miq71.starworks.R
import com.miq71.starworks.databinding.ItemEngineerBinding
import com.miq71.starworks.model.engineer.EngineerModel
import com.miq71.starworks.model.skill.SkillModel
import com.miq71.starworks.remote.ApiClient
import com.miq71.starworks.remote.ApiClient.Companion.BASE_URL_IMAGE

class SearchEngineerAdapter : RecyclerView.Adapter<SearchEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemEngineerBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<EngineerModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(en: EngineerModel) {
            bind.engineer = en

            if (en.enProfile != null) {
                bind.imageUrl = BASE_URL_IMAGE + en.enProfile
            } else {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE
            }

            setSkillRecyclerView()
            setSkillAdapter(en)

            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(en)
            }
        }

        private fun setSkillRecyclerView() {
            bind.rvSkill.layoutManager = FlexboxLayoutManager(itemView.context)
            val adapter = SearchSkillAdapter()
            bind.rvSkill.adapter = adapter
        }

        private fun setSkillAdapter(en: EngineerModel) {
            val listSkill = en.enSkill?.map {
                SkillModel(
                    sk_id = it.sk_id,
                    sk_skill_name = it.sk_skill_name
                )
            }

            (bind.rvSkill.adapter as SearchSkillAdapter).addList(listSkill!!)

            if (listSkill.size > 3) {
                bind.moreSkill = "+${listSkill.size - 3}"
                bind.tvMore.visibility = View.VISIBLE
            } else {
                bind.tvMore.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_engineer, parent, false)
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
        fun onItemClick(data: EngineerModel)
    }

    fun addList(list: List<EngineerModel>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}