package com.miq71.starworks.activity.main.fragment.project.engineer

import com.miq71.starworks.model.hire.HireModel

interface ProjectHiringContract {
    interface View {
        fun onResultSuccess(list: List<HireModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(enId: Int)
    }
}