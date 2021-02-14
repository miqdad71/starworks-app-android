package com.miqdad71.starworks.ui.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.company.CompanyResponse
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.CompanyAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SettingsViewModel : ViewModel(), CoroutineScope {
    private lateinit var serviceAccount: AccountAPI
    private lateinit var serviceEngineer: EngineerAPI
    private lateinit var serviceCompany: CompanyAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(
        serviceAccount: AccountAPI,
        serviceEngineer: EngineerAPI,
        serviceCompany: CompanyAPI
    ) {
        this@SettingsViewModel.serviceAccount = serviceAccount
        this@SettingsViewModel.serviceEngineer = serviceEngineer
        this@SettingsViewModel.serviceCompany = serviceCompany
    }

    fun serviceUpdateAccount(field: String, value: String, acId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.updateAccount(
                        acId = acId,
                        fields = map
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 404 -> {
                            failStatus = "Data not found!"
                        }
                        e.code() == 400 -> {
                            failStatus = "Fail to update data!"
                        }
                        else -> {
                            failStatus = "Internal Server Error!"
                        }
                    }
                }
            }
            if (failStatus.isNotEmpty()) {
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }
            if (response is AccountResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateEngineer(field: String, value: String, enId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceEngineer.updateEngineer(
                        enId = enId,
                        fields = map
                    )
                } catch (e: HttpException) {

                    when {
                        e.code() == 404 -> {
                            failStatus = "Data not found!"
                        }
                        e.code() == 400 -> {
                            failStatus = "Fail to update data!"
                        }
                        else -> {
                            failStatus = "Internal Server Error!"
                        }
                    }
                }
            }
            if (failStatus.isNotEmpty()) {
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is EngineerResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message

                }
            }
        }
    }

    fun serviceUpdateCompany(field: String, value: String, cnId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceCompany.updateCompany(
                        cnId = cnId,
                        fields = map
                    )
                } catch (e: HttpException) {

                    when {
                        e.code() == 404 -> {
                            failStatus = "Data not found!"
                        }
                        e.code() == 400 -> {
                            failStatus = "Fail to update data!"
                        }
                        else -> {
                            failStatus = "Internal Server Error!"
                        }
                    }
                }
            }

            if (response is CompanyResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }
}