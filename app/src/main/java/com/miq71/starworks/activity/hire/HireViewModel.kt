package com.miq71.starworks.activity.hire

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miq71.starworks.model.hire.HireResponse
import com.miq71.starworks.service.HireApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HireViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: HireApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: HireApiService) {
        this@HireViewModel.service = service
    }

    fun serviceCreateApi(enId: Int, pjId: Int, hrPrice: Long, hrMessage: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.createHire(
                        enId = enId,
                        pjId = pjId,
                        hrPrice = hrPrice,
                        hrMessage = hrMessage
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to hire engineer!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is HireResponse) {
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