package com.miqdad71.starworks.view.webview

interface WebViewListener {
    fun onPageStarted()
    fun onPageFinish()
    fun onShouldOverrideUrl(redirectUrl: String)
    fun onProgressChange(progress: Int)
}