package com.miq71.starworks.activity.main.fragment.project.company

import com.miq71.starworks.model.project.ProjectModel

interface ProjectCompanyContract {
    interface View {
        fun onResultSuccess(list: List<ProjectModel>)
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