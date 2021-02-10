package com.miqdad71.starworks.ui.activity.main.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.project.ProjectResponse
import com.miqdad71.starworks.serviceapi.ProjectAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.util.*
import kotlin.coroutines.CoroutineContext

class ProjectViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ProjectAPI
    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

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
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.createProject(
                        cnId = cnId,
                        pjProjectName = pjProjectName,
                        pjDeadline = pjDeadline,
                        pjDescription = pjDescription,
                        image = image
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 400 -> {
                            failStatus = "Fail to add data!"
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

            if (response is ProjectResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
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
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateProject(
                        pjId = pjId,
                        pjProjectName = pjProjectName,
                        pjDeadline = pjDeadline,
                        pjDescription = pjDescription,
                        image = image
                    )
                } catch (e: HttpException) {
                    when {
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

            if (response is ProjectResponse) {
                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }

        }
    }
}
