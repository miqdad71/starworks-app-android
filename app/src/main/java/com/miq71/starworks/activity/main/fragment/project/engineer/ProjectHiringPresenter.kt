package com.miq71.starworks.activity.main.fragment.project.engineer

import com.miq71.starworks.model.hire.HireModel
import com.miq71.starworks.model.hire.HireResponse
import com.miq71.starworks.service.HireApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProjectHiringPresenter(private val service: HireApiService) : CoroutineScope, ProjectHiringContract.Presenter {

    private var view: ProjectHiringContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProjectHiringContract.View) {
        this@ProjectHiringPresenter.view = view
    }

    override fun unbind() {
        this@ProjectHiringPresenter.view = null
    }

    override fun callService(enId: Int) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllHire(enId = enId)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No Data Hiring!")
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

            if (response is HireResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        HireModel(
                            hrId = it.hrId,
                            enId = it.enId,
                            pjId = it.pjId,
                            hrPrice = it.hrPrice,
                            hrMessage = it.hrMessage,
                            hrStatus = it.hrStatus,
                            hrDateConfirm =it.pjDeadline.split('T')[0],
                            pjProjectName = it.pjProjectName,
                            pjDescription = it.pjDescription,
                            pjDeadline = it.pjDeadline.split('T')[0],
                            pjImage = it.pjImage,
                            cnCompany = it.cnCompany,
                            cnField = it.cnField,
                            cnCity = it.cnCity,
                            cnProfile = it.cnProfile,
                            enProfile = it.enProfile,
                            acName = it.acName,
                            acEmail = it.acEmail,
                            acPhone = it.acPhone
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