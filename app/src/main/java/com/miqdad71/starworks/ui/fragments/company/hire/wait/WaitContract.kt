package com.miqdad71.starworks.ui.fragments.company.hire.wait

import com.miqdad71.starworks.data.model.hire.HireModel

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