package com.miq71.starworks.activity.detail_profile.fragment.experience

import com.miq71.starworks.model.experience.ExperienceModel
import com.miq71.starworks.model.experience.ExperienceResponse
import com.miq71.starworks.service.ExperienceApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DetailProfileExperiencePresenter(private val service: ExperienceApiService) : CoroutineScope, DetailProfileExperienceContract.Presenter {

    private var view: DetailProfileExperienceContract.View? = null

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
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data experience!")
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