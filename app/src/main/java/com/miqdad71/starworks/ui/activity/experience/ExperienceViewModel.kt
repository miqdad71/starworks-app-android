package com.miqdad71.starworks.ui.activity.experience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.ExperienceAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ExperienceViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ExperienceAPI

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ExperienceAPI) {
        this@ExperienceViewModel.service = service
    }
    fun createAPI(
        enId: Int,
        exPosition: String,
        exCompany: String,
        exStart: String,
        exEnd: String,
        exDescription: String
    ) {
        launch {
            try {
                service.createExperience(
                    enId = enId,
                    exPosition = exPosition,
                    exCompany = exCompany,
                    exStart = exStart,
                    exEnd = exEnd,
                    exDescription = exDescription
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun updateAPI(
        exId: Int,
        exPosition: String,
        exCompany: String,
        exStart: String,
        exEnd: String,
        exDescription: String
    ) {
        launch {
            try {
                service.updateExperience(
                    exId = exId,
                    exPosition = exPosition,
                    exCompany = exCompany,
                    exStart = exStart,
                    exEnd = exEnd,
                    exDescription = exDescription
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
    fun deleteAPI(
        exId: Int
    ) {
        launch {
            try {
                service.deleteExperience(
                    exId = exId
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}