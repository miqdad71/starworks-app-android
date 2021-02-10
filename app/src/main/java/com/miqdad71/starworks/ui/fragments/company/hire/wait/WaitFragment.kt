package com.miqdad71.starworks.ui.fragments.company.hire.wait

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.databinding.FragmentWaitBinding
import com.miqdad71.starworks.ui.activity.detail.company.ProjectDetailActivity
import com.miqdad71.starworks.ui.base.BaseFragmentCoroutine
import com.miqdad71.starworks.ui.fragments.company.hire.wait.adapter.WaitHireAdapter

class WaitFragment : BaseFragmentCoroutine<FragmentWaitBinding>(), WaitContract.View {
    private var presenter: WaitPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_wait
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = WaitPresenter(createApi(activity))

        setHiringProjectRecyclerView()
    }

    override fun onResultSuccess(list: List<HireModel>) {
        (binding.rvHire.adapter as WaitHireAdapter).addList(list)
        binding.rvHire.visibility = View.VISIBLE
        binding.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            binding.rvHire.visibility = View.GONE
            binding.tvDataNotFound.visibility = View.VISIBLE
            binding.dataNotFound = message
        }
    }

    override fun showLoading() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.rvHire.visibility = View.GONE
        binding.tvDataNotFound.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        binding.shimmerViewContainer.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        binding.shimmerViewContainer.startShimmerAnimation()

        presenter?.bindToView(this@WaitFragment)
        presenter?.callService(sharedPref.getIdCompany())
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setHiringProjectRecyclerView() {
        binding.rvHire.isNestedScrollingEnabled = false
        binding.rvHire.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = WaitHireAdapter()
        binding.rvHire.adapter = adapter

        adapter.setOnItemClickCallback(object : WaitHireAdapter.OnItemClickCallback {
            override fun onItemClick(data: HireModel) {
                val intent = Intent(activity, ProjectDetailActivity::class.java)
                intent.putExtra("hr_id", data.hrId)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("pj_id", data.pjId)
                intent.putExtra("hr_price", data.hrPrice)
                intent.putExtra("hr_message", data.hrMessage)
                intent.putExtra("hr_status", data.hrStatus)
                intent.putExtra("hr_date_confirm", data.hrDateConfirm)
                intent.putExtra("pj_project_name", data.pjProjectName)
                intent.putExtra("pj_deadline", data.pjDeadline)
                intent.putExtra("pj_description", data.pjDescription)
                intent.putExtra("pj_image", data.pjImage)
                intent.putExtra("cn_company", data.cnCompany)
                intent.putExtra("cn_field", data.cnField)
                intent.putExtra("cn_city", data.cnCity)
                intent.putExtra("en_profile", data.enProfile)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("ac_email", data.acEmail)
                intent.putExtra("ac_phone", data.acPhone)
                startActivity(intent)
            }
        })
    }
}