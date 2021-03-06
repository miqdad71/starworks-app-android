package com.miqdad71.starworks.ui.activity.detail.profile

import android.util.Log
import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.data.model.skill.SkillResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProfileDetailPresenter(
    private val serviceAccount: AccountAPI,
    private val serviceEngineer: EngineerAPI,
    private val serviceSkill: SkillAPI,
    private val serviceHire: HireAPI
) : CoroutineScope,
    ProfileDetailContract.Presenter {

    private var view: ProfileDetailContract.View? = null
    private var failStatus = ""
    private var failStatusHire = false

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProfileDetailContract.View) {
        this@ProfileDetailPresenter.view = view
    }

    override fun unbind() {
        this@ProfileDetailPresenter.view = null
    }

    override fun callServiceAccount(acId: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.detailAccount(acId = acId!!)
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is AccountResponse) {
                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessAccount(data)
                }
            }
        }
    }

    override fun callServiceEngineer(acId: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceEngineer.getDetailEngineer(acId = acId!!)
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is EngineerResponse) {
                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessEngineer(data)
                }
            }
        }
    }

    override fun callServiceSkill(enId: Int?) {
        launch {
//            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceSkill.getAllSkill(enId = enId!!)
                } catch (e: HttpException) {
//                    view?.hideLoading()

                    when {
                        e.code() == 404 -> {
                            failStatus = "No data skill!"
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

    override fun callServiceIsHire(cnId: Int?, enId: Int?) {
        launch {
//            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceHire.checkIsHire(
                        cnId = cnId!!,
                        enId = enId!!
                    )
                } catch (e: HttpException) {
                    failStatusHire = false

                }
            }

            if (!failStatusHire) {
                view?.onResultFailHire(failStatusHire)
            }

            if (response is HireResponse) {
                view?.hideLoading()

                if (response.success) {
                    if (response.data[0].hrStatus == "wait" || response.data[0].hrStatus == "approve") {
                        if (response.data[0].cnId != cnId)
                            view?.onResultSuccessHire(false)
                    } else {
                        view?.onResultSuccessHire(true)
                    }
                } else {
                    view?.onResultFailHire(false)
                }
            }
        }
    }
}