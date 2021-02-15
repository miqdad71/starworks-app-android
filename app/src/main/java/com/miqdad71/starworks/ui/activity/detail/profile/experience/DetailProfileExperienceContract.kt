package com.miqdad71.starworks.ui.activity.detail.profile.experience

import com.miqdad71.starworks.data.model.experience.ExperienceModel

interface DetailProfileExperienceContract {
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