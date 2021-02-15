package com.miqdad71.starworks.ui.activity.detail.profile.portfolio

import com.miqdad71.starworks.data.model.portfolio.PortfolioModel

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