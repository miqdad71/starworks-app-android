package com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.portfolio.PortfolioModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentPortfolioBinding
import com.miqdad71.starworks.serviceapi.PortfolioAPI
import com.miqdad71.starworks.ui.activity.portfolio.PortfolioActivity
import com.miqdad71.starworks.ui.adapter.portfolio.PortfolioEngineerAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class PortfolioEngineerFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private lateinit var service: PortfolioAPI

    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portfolio, container, false)

        sharedPref = SharedPreference(requireContext())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(PortfolioAPI::class.java)

        setupPortfolioRecyclerView()
        getAllPortfolio()
        return binding.root
    }

    private fun setupPortfolioRecyclerView() {
        binding.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = PortfolioEngineerAdapter()
        binding.rvPortfolio.adapter = adapter

        adapter.setOnItemClickCallback(object : PortfolioEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: PortfolioModel) {
                val intent = Intent(activity, PortfolioActivity::class.java)
                intent.putExtra("pr_id", data.pr_id)
                intent.putExtra("en_id", data.en_id)
                intent.putExtra("pr_app", data.pr_app)
                intent.putExtra("pr_description", data.pr_description)
                intent.putExtra("pr_link_pub", data.pr_link_pub)
                intent.putExtra("pr_link_repo", data.pr_link_repo)
                intent.putExtra("pr_work_place", data.pr_work_place)
                intent.putExtra("pr_type", data.pr_type)
                intent.putExtra("pr_image", data.pr_image)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }

    private fun getAllPortfolio() {
        coroutineScope.launch {
            try {
                val resultData = service.getAllPortfolio(sharedPref.getIdEngineer())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    PortfolioModel(
                        pr_id = it.prId,
                        en_id = it.enId,
                        pr_app = it.prApp,
                        pr_description = it.prDescription,
                        pr_link_pub = it.prLinkPub,
                        pr_link_repo = it.prLinkRepo,
                        pr_work_place = it.prWorkPlace,
                        pr_type = it.prType,
                        pr_image = it.prImage
                    )
                }

                (binding.rvPortfolio.adapter as PortfolioEngineerAdapter).addList(list)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}