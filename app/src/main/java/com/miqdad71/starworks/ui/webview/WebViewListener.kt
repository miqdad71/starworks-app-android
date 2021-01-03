package com.miqdad71.starworks.ui.webview

interface WebViewListener {
    fun onPageStarted()
    fun onPageFinish()
    fun onShouldOverrideUrl(redirectUrl: String)
    fun onProgressChange(progress: Int)
}