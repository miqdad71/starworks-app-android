package com.miq71.starworks.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio

import com.miq71.starworks.model.portfolio.PortfolioModel

interface PortfolioContract {
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