package com.miq71.starworks.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miq71.starworks.model.account.LoginResponse
import com.miq71.starworks.service.AccountApiService
import com.miq71.starworks.util.SharedPreference
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountApiService
    private lateinit var sharedPref: SharedPreference

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountApiService) {
        this@LoginViewModel.service = service
    }

    fun setSharedPref(sharedPref: SharedPreference) {
        this@LoginViewModel.sharedPref = sharedPref
    }

    fun serviceApi(email: String, password: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(
                        email = email,
                        password = password
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Account not registered"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Password is invalid!"
                            }
                            else -> {
                                onFailLiveData.value = "Login is fail! Please try again later!"
                            }
                        }
                    }
                }
            }

            if (response is LoginResponse) {
                isLoadingLiveData.value = false

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

                    onSuccessLiveData.value = true
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }
}