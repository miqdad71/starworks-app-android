package com.miqdad71.starworks.ui.activity.main.engineer.img_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.serviceapi.EngineerAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ImageProfileEngineerViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: EngineerAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: EngineerAPI) {
        this@ImageProfileEngineerViewModel.service = service
    }

    fun serviceUpdateImageEngineer(enId: Int, image: MultipartBody.Part? = null) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateEngineerImage(
                        enId = enId,
                        image = image
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
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is EngineerResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                }
            }
        }
    }
}