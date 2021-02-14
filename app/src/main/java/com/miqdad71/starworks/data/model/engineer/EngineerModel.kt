package com.miqdad71.starworks.data.model.engineer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EngineerModel(
    val enId: Int? = 0,
    val acId: Int? = 0,
    val acName: String? = null,
    val enJobTitle: String? = null,
    val enJobType: String? = null,
    val enDomicile: String? = null,
    val enDescription: String? = null,
    val enProfile: String? = null
) : Parcelable