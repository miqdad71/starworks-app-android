package com.miq71.starworks.activity.main.fragment.profile.engineer

import android.util.Log
import com.miq71.starworks.model.account.AccountResponse
import com.miq71.starworks.model.engineer.EngineerResponse
import com.miq71.starworks.model.skill.SkillModel
import com.miq71.starworks.model.skill.SkillResponse
import com.miq71.starworks.service.AccountApiService
import com.miq71.starworks.service.EngineerApiService
import com.miq71.starworks.service.SkillApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProfileEngineerPresenter(
    private val serviceAccount: AccountApiService,
    private val serviceEngineer: EngineerApiService,
    private val serviceSkill: SkillApiService
) : CoroutineScope,
    ProfileEngineerContract.Presenter {

    private var view: ProfileEngineerContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProfileEngineerContract.View) {
        this@ProfileEngineerPresenter.view = view
    }

    override fun unbind() {
        this@ProfileEngineerPresenter.view = null
    }

    override fun callServiceAccount(acId: Int?) {
        view?.showLoading()

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.detailAccount(acId = acId!!)
                } catch (t: Throwable) {
                    withContext(Dispatchers.Main) {
                        Log.d("msg", "${t.message}")
                    }
                }
            }

            if (response is AccountResponse) {
                view?.hideLoading()

                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessAccount(data)
                }
            }
        }
    }

    override fun callServiceEngineer(acId: Int?) {
        view?.showLoading()

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceEngineer.getDetailEngineer(acId = acId!!)
                } catch (t: Throwable) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()
                    }
                }
            }

            if (response is EngineerResponse) {
                view?.hideLoading()

                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessEngineer(data)
                }
            }
        }
    }

    override fun callServiceSkill(enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceSkill.getAllSkill(enId = enId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data skill!")
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

            if (response is SkillResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        SkillModel(
                            sk_id = it.sk_id,
                            sk_skill_name = it.skSkillName
                        )
                    }

                    view?.onResultSuccessSkill(list)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }
}