package com.miq71.starworks.activity.main.fragment.profile.engineer.fragment_engineer_user.experience

import com.miq71.starworks.model.experience.ExperienceModel

interface ExperienceContract {
    interface View {
        fun onResultSuccess(list: List<ExperienceModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(enId: Int?)
    }
}