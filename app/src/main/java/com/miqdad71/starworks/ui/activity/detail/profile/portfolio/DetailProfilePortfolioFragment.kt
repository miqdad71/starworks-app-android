package com.miqdad71.starworks.ui.activity.detail.profile.portfolio

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.portfolio.PortfolioModel
import com.miqdad71.starworks.databinding.FragmentPortfolioBinding
import com.miqdad71.starworks.ui.activity.detail.profile.portfolio.adapter.ProfileDetailPortfolioAdapter
import com.miqdad71.starworks.ui.base.BaseFragmentCoroutine
import com.miqdad71.starworks.util.Utils

class DetailProfilePortfolioFragment(private val enId: Int) : BaseFragmentCoroutine<FragmentPortfolioBinding>(), DetailProfilePortfolioContract.View {
    private var presenter: DetailProfilePortfolioPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_portfolio
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailProfilePortfolioPresenter(createApi(activity))

        setupPortfolioRecyclerView()
    }

    override fun onResultSuccess(list: List<PortfolioModel>) {
        (binding.rvPortfolio.adapter as ProfileDetailPortfolioAdapter).addList(list)
        binding.rvPortfolio.visibility = View.VISIBLE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            binding.rvPortfolio.visibility = View.GONE
            binding.tvDataNotFound.visibility = View.VISIBLE
            binding.dataNotFound = message
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@DetailProfilePortfolioFragment)
        presenter?.callService(enId)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setupPortfolioRecyclerView() {
        val offsetPx = resources.getDimension(R.dimen.bottom_end_recyclerview_home)
        val bottomOffsetDecoration = Utils.Companion.BottomOffsetDecoration(offsetPx.toInt())
        binding.rvPortfolio.addItemDecoration(bottomOffsetDecoration)
        binding.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfileDetailPortfolioAdapter()
        binding.rvPortfolio.adapter = adapter
    }
}