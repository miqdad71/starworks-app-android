package com.miq71.starworks.activity.main.fragment.search

import com.miq71.starworks.model.engineer.EngineerModel

interface SearchContract {
    interface View {
        fun onResultSuccess(list: List<EngineerModel>, totalPages: Int)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceSearch(search: String?, page: Int?)
        fun callServiceFilter(filter: Int?)
    }
}