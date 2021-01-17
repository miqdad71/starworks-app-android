package com.miqdad71.starworks.ui.activity.main.engineer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*
import kotlin.coroutines.CoroutineContext

class EngineerViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: EngineerAPI
    private lateinit var serviceAccount: AccountAPI

    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: EngineerAPI) {
        this@EngineerViewModel.service = service
    }

    fun setServiceAccount(serviceAccount: AccountAPI) {
        this@EngineerViewModel.serviceAccount = serviceAccount
    }

    fun updateAPI(
        enId: Int,
        acId: Int,
        acName: String,
        enJobTitle: String,
        enJobType: String,
        enDomicile: String,
        enDescription: String
        ) {
        launch {
            try {
                serviceAccount.updateAccount(
                    acId = acId,
                    acName = acName
                )
                service.updateEngineer(
                    enId = enId,
                    enJobTitle = enJobTitle,
                    enJobType = enJobType,
                    enDomicile = enDomicile,
                    enDescription = enDescription
                    )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    /*fun deleteAPI(
        skId: Int
    ) {
        launch {
            try {
                service.deleteSkill(
                    skId = skId
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }*/

}