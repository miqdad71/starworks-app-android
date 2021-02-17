package com.miqdad71.starworks.ui.fragments.company.hire.wait

import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.serviceapi.HireAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class WaitPresenter(private val service: HireAPI) : CoroutineScope, WaitContract.Presenter {
    private var view: WaitContract.View? = null
    private var failStatus = ""

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: WaitContract.View) {
        this@WaitPresenter.view = view
    }

    override fun unbind() {
        this@WaitPresenter.view = null
    }

    override fun callService(cnId: Int) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllHireCompany(
                        cnId = cnId,
                        status = "wait"
                    )
                } catch (e: HttpException) {
                        

                        when {
                            e.code() == 404 -> {
                                failStatus = "No Data Wait Hire!"
                            }
                            e.code() == 400 -> {
                                failStatus = "expired"
                            }
                            else -> {
                                failStatus = "Server under maintenance!"
                            }
                        }
                }
            }

            if (failStatus.isNotEmpty()) {
                view?.onResultFail(failStatus)
                failStatus = ""
            }

            if (response is HireResponse) {
                

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