package com.miqdad71.starworks.ui.activity.main.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.api.ProjectApi
import com.miqdad71.starworks.data.model.project.ProjectResponse
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProjectViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ProjectApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ProjectApi) {
        this@ProjectViewModel.service = service
    }

    fun serviceCreateApi(
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
                        pjDescription = pjDescription
//                        image = image
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to add data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateApi(
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
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Data not found!"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to update data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceDeleteApi(pjId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.deleteProject(
                        pjId = pjId
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Data not found!"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to delete data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }
}