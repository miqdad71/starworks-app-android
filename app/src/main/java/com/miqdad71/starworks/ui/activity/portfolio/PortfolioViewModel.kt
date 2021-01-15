package com.miqdad71.starworks.ui.activity.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.PortfolioAPI
import com.miqdad71.starworks.serviceapi.ProjectAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.http.Part
import java.util.*
import kotlin.coroutines.CoroutineContext

class PortfolioViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: PortfolioAPI
    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: PortfolioAPI) {
        this@PortfolioViewModel.service = service
    }
    fun createAPI(
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
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun updateAPI(
        prId: Int,
        prApp: RequestBody,
        prDescription: RequestBody,
        prLinkPub: RequestBody,
        prLinkRepo: RequestBody,
        prWorkPlace: RequestBody,
        prType: RequestBody,
        image: MultipartBody.Part
    ) {
        launch {
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
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}