package com.miq71.starworks.activity.main.fragment.hiring.fragment_hiring.wait

import com.miq71.starworks.model.hire.HireModel

interface WaitContract {
    interface View {
        fun onResultSuccess(list: List<HireModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(cnId: Int)
    }
}