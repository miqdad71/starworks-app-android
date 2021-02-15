package com.miqdad71.starworks.ui.activity.detail.profile.experience

import com.miqdad71.starworks.data.model.experience.ExperienceModel
import com.miqdad71.starworks.data.model.experience.ExperienceResponse
import com.miqdad71.starworks.serviceapi.ExperienceAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DetailProfileExperiencePresenter(private val service: ExperienceAPI) : CoroutineScope,
    DetailProfileExperienceContract.Presenter {

    private var view: DetailProfileExperienceContract.View? = null
    private var failStatus = ""

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: DetailProfileExperienceContract.View) {
        this@DetailProfileExperiencePresenter.view = view
    }

    override fun unbind() {
        this@DetailProfileExperiencePresenter.view = null
    }

    override fun callService(enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllExperience(enId = enId!!)
                } catch (e: HttpException) {
                    view?.hideLoading()

                    when {
                        e.code() == 404 -> {
                            failStatus = "No data experience!"
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

            if (response is ExperienceResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        ExperienceModel(
                            ex_id = it.exId,
                            en_id = it.enId,
                            ex_position = it.exPosition,
                            ex_company = it.exCompany,
                            ex_start = it.exStart.split('T')[0],
                            ex_end = it.exEnd.split('T')[0],
                            ex_description = it.exDescription
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