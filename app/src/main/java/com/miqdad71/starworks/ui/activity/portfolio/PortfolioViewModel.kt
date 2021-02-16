package com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.data.model.portfolio.PortfolioResponse
import com.miqdad71.starworks.serviceapi.PortfolioAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class PortfolioViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: PortfolioAPI

    private var failStatus = ""
    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: PortfolioAPI) {
        this@PortfolioViewModel.service = service
    }

    fun serviceCreateApi(
        enId: RequestBody,
        prApp: RequestBody,
        prDescription: RequestBody,
        prLinkPub: RequestBody,
        prLinkRepo: RequestBody,
        prWorkPlace: RequestBody,
        prType: RequestBody,
        image: MultipartBody.Part
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.createPortfolio(
                        enId = enId,
                        prApp = prApp,
                        prDescription = prDescription,
                        prLinkPub = prLinkPub,
                        prLinkRepo = prLinkRepo,
                        prWorkPlace = prWorkPlace,
                        prType = prType,
                        image = image
                    )
                } catch (e: HttpException) {


                    when {
                        e.code() == 400 -> {
                            failStatus = "Fail to add data!"
                        }
                        e.code() == 404 -> {
                            failStatus = "Image to large!"
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

            if (response is PortfolioResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateApi(
        prId: Int,
        prApp: RequestBody,
        prDescription: RequestBody,
        prLinkPub: RequestBody,
        prLinkRepo: RequestBody,
        prWorkPlace: RequestBody,
        prType: RequestBody,
        image: MultipartBody.Part? = null
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updatePortfolio(
                        prId = prId,
                        prApp = prApp,
                        prDescription = prDescription,
                        prLinkPub = prLinkPub,
                        prLinkRepo = prLinkRepo,
                        prWorkPlace = prWorkPlace,
                        prType = prType,
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
                onSuccessLiveData.value = ""
                onFailLiveData.value = failStatus
                failStatus = ""
            }

            if (response is PortfolioResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }

    fun serviceDeleteApi(prId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.deletePortfolio(
                        prId = prId
                    )
                } catch (e: HttpException) {
                    when {
                        e.code() == 404 -> {
                            failStatus = "Data not found!"
                        }
                        e.code() == 400 -> {
                            failStatus = "Fail to delete data!"
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

            if (response is PortfolioResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = response.message
                }
            }
        }
    }
}