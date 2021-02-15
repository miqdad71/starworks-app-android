package com.miqdad71.starworks.ui.activity.detail.profile.experience

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.experience.ExperienceModel
import com.miqdad71.starworks.databinding.FragmentExperienceBinding
import com.miqdad71.starworks.ui.activity.detail.profile.experience.adapter.ProfileDetailExperienceAdapter
import com.miqdad71.starworks.ui.base.BaseFragmentCoroutine

class DetailProfileExperienceFragment(private val enId: Int) : BaseFragmentCoroutine<FragmentExperienceBinding>(), DetailProfileExperienceContract.View {
    private var presenter: DetailProfileExperiencePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_experience
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailProfileExperiencePresenter(createApi(activity))

        setupExperienceRecyclerView()
    }

    override fun onResultSuccess(list: List<ExperienceModel>) {
        (binding.rvExperience.adapter as ProfileDetailExperienceAdapter).addList(list)
        binding.rvExperience.visibility = View.VISIBLE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            binding.rvExperience.visibility = View.GONE
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
        presenter?.bindToView(this@DetailProfileExperienceFragment)
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

    private fun setupExperienceRecyclerView() {
        binding.rvExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfileDetailExperienceAdapter()
        binding.rvExperience.adapter = adapter
    }
}