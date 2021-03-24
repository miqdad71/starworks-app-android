package com.miq71.starworks.activity.main.fragment.home

import com.miq71.starworks.model.engineer.EngineerModel

interface HomeContract {
    interface View {
        fun onResultSuccess(list: List<EngineerModel>, totalPages: Int)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(page: Int)
    }
}