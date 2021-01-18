package com.miqdad71.starworks.ui.activity.detail.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.HireAPI
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HireViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: HireAPI
    val onSuccessLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: HireAPI) {
        this@HireViewModel.service = service
    }

    fun createAPI(
        enId: Int,
        pjId: Int,
        hrPrice: Long,
        hrMessage: String
        ) {
        launch {
            try {
                service.createHire(
                    enId = enId,
                    pjId = pjId,
                    hrPrice = hrPrice,
                    hrMessage = hrMessage
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}