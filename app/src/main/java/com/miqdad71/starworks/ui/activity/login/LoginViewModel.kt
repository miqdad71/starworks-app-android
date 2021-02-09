package com.miqdad71.starworks.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.account.LoginResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountAPI
    private lateinit var sharedPref: SharedPreference
    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountAPI) {
        this@LoginViewModel.service = service
    }

    fun setSharedPref(sharedPref: SharedPreference) {
        this@LoginViewModel.sharedPref = sharedPref
    }

    fun createAPI(email: String, password: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(
                        email = email,
                        password = password
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 404 -> {
                            failStatus = "Account not registered"
                        }
                        e.code() == 400 -> {
                            failStatus = "Password is invalid!"
                        }
                        else -> {
                            failStatus = "Login is fail! Please try again later!"
                        }
                    }
                }
            }

            if (failStatus.isNotEmpty()) {
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is LoginResponse) {

                if (response.success) {
                    val data = response.data
                    val id: Int?

                    id = if (data.acLevel == 0) {
                        data.enId
                    } else {
                        data.cnId
                    }

                    sharedPref.createAccountUser(
                        id = id!!,
                        acId = data.acId,
                        acLevel = data.acLevel,
                        acName = data.acName,
                        acEmail = data.acEmail,
                        token = data.token,
                        exp = data.expired
                    )
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }
}

