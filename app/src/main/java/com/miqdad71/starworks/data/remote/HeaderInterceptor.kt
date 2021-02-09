package com.miqdad71.starworks.data.remote

import android.content.Context
import com.miqdad71.starworks.util.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        val sharedPref = SharedPreference(context)

        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer ${sharedPref.getToken()}")
                .build()
        )
    }
}