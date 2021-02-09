package com.miqdad71.starworks.ui.activity.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.account.SignUpResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SignUpViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountAPI) {
        this@SignUpViewModel.service = service
    }

    fun serviceEngineerApi(acName: String, acEmail: String, acPhone: String, acPassword: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.signUpEngineerAccount(
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        acPassword = acPassword,
                        acLevel = 0
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 400 -> {
                            failStatus = "Email has registered!"
                        }
                        else -> {
                            failStatus = "Fail to registration! Please try again later!"
                        }
                    }
                }
            }

            if (failStatus.isNotEmpty()) {
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is SignUpResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }

    fun serviceCompanyApi(
        acName: String,
        acEmail: String,
        acPhone: String,
        acPassword: String,
        cnCompany: String,
        cnPosition: String
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.signUpCompanyAccount(
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        acPassword = acPassword,
                        acLevel = 1,
                        cnCompany = cnCompany,
                        cnPosition = cnPosition
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 400 -> {
                            failStatus = "Email has registered!"
                        }
                        else -> {
                            failStatus = "Fail to registration! Please try again later!"
                        }
                    }
                }
            }

            if (failStatus.isNotEmpty()) {
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is SignUpResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }
}