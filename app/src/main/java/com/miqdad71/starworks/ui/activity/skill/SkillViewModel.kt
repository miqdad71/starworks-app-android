package com.miqdad71.starworks.ui.activity.skill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miqdad71.starworks.serviceapi.ProjectAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.util.*
import kotlin.coroutines.CoroutineContext

class SkillViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: SkillAPI
    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: SkillAPI) {
        this@SkillViewModel.service = service
    }

    fun createAPI(
        enId: Int,
        skSkillName: String
    ) {
        launch {
            try {
                service.createSkill(
                    enId = enId,
                    skSkillName = skSkillName
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun updateAPI(
        skId: Int,
        skSkillName: String
    ) {
        launch {
            try {
                service.updateSkill(
                    skId = skId,
                    skSkillName = skSkillName
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}