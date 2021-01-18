package com.miqdad71.starworks.ui.activity.main.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.ProjectAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.util.*
import kotlin.coroutines.CoroutineContext

class ProjectViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ProjectAPI
    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ProjectAPI) {
        this@ProjectViewModel.service = service
    }

    fun createAPI(
        cnId: RequestBody,
        pjProjectName: RequestBody,
        pjDeadline: RequestBody,
        pjDescription: RequestBody,
        image: MultipartBody.Part
    ) {
        launch {
            try {
                service.createProject(
                    cnId = cnId,
                    pjProjectName = pjProjectName,
                    pjDeadline = pjDeadline,
                    pjDescription = pjDescription,
                    image = image
                )
                onSuccessLiveData.value=true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
    fun updateAPI(
        pjId: Int,
        pjProjectName: RequestBody,
        pjDeadline: RequestBody,
        pjDescription: RequestBody,
        image: MultipartBody.Part? = null
    ) {
        launch {
            try {
                service.updateProject(
                    pjId = pjId,
                    pjProjectName = pjProjectName,
                    pjDeadline = pjDeadline,
                    pjDescription = pjDescription,
                    image = image
                )
                onSuccessLiveData.value=true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}