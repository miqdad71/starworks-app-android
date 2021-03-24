package com.miq71.starworks.activity.main.fragment.project.company

import com.miq71.starworks.model.project.ProjectModel
import com.miq71.starworks.model.project.ProjectResponse
import com.miq71.starworks.service.ProjectApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProjectCompanyPresenter(private val service: ProjectApiService) : CoroutineScope, ProjectCompanyContract.Presenter {
    private var view: ProjectCompanyContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProjectCompanyContract.View) {
        this@ProjectCompanyPresenter.view = view
    }

    override fun unbind() {
        this@ProjectCompanyPresenter.view = null
    }

    override fun callService(cnId: Int) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllProject(cnId)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No Data Project!")
                            }
                            e.code() == 400 -> {
                                view?.onResultFail("expired")
                            }
                            else -> {
                                view?.onResultFail("Server under maintenance!")
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        ProjectModel(
                            pjId = it.pjId,
                            cnId = it.cnId,
                            pjProjectName = it.pjProjectName,
                            pjDescription = it.pjDescription,
                            pjDeadline = it.pjDeadline.split('T')[0],
                            pjImage = it.pjImage,
                        )
                    }

                    view?.onResultSuccess(list)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }
}