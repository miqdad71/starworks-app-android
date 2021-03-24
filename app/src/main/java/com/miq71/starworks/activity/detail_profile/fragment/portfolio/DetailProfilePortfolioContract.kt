package com.miq71.starworks.activity.detail_profile.fragment.portfolio

import com.miq71.starworks.model.portfolio.PortfolioModel

interface DetailProfilePortfolioContract {
    interface View {
        fun onResultSuccess(list: List<PortfolioModel>)
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