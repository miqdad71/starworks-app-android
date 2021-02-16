package com.miqdad71.starworks.ui.activity.forgetpassword.check_email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.account.VerifyResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class CheckEmailViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val onSetAccountId = MutableLiveData<Int>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountAPI) {
        this@CheckEmailViewModel.service = service
    }

    fun serviceApi(email: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.checkEmail(
                        email = email
                    )
                } catch (e: HttpException) {

                        when {
                            e.code() == 404 -> {
                                failStatus = "Account not registered"
                            }
                            else -> {
                                failStatus = "Fail to check email! Please try again later!"
                            }
                        }
                }
            }

            if (failStatus.isNotEmpty()) {
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is VerifyResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onSetAccountId.value = response.data[0].acId
                }
            }
        }
    }
}