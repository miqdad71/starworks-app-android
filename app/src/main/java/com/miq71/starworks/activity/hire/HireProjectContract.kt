package com.miq71.starworks.activity.hire

import com.miq71.starworks.model.project.ProjectModel

interface HireProjectContract {
    interface View {
        fun onResultSuccess(list: List<ProjectModel>)
        fun onResultFail(message: String)
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(cnId: Int)
    }
}