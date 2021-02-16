package com.miqdad71.starworks.ui.activity.forgetpassword.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ResetPasswordViewModel : ViewModel(), CoroutineScope {
    private lateinit var serviceAccount: AccountAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(serviceAccount: AccountAPI) {
        this@ResetPasswordViewModel.serviceAccount = serviceAccount
    }

    fun serviceUpdate(acId: Int, acPassword: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.resetPassword(
                        acId = acId,
                        acPassword = acPassword
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        isLoadingLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                failStatus = "Data not found!"
                            }
                            e.code() == 400 -> {
                                failStatus = "Fail to reset password!"
                            }
                            else -> {
                                failStatus = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (failStatus.isNotEmpty()) {
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is AccountResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                }
            }
        }
    }
}