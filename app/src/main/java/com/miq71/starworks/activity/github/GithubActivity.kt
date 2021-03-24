package com.miq71.starworks.activity.github

import android.os.Bundle
import android.util.Log
import android.view.View
import com.miq71.starworks.R
import com.miq71.starworks.base.BaseActivity
import com.miq71.starworks.activity.github.client.ChromeClient
import com.miq71.starworks.activity.github.client.WebClient
import com.miq71.starworks.activity.github.client.WebViewListener
import com.miq71.starworks.databinding.ActivityGithubBinding

class GithubActivity : BaseActivity<ActivityGithubBinding>(), WebViewListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_github
        super.onCreate(savedInstanceState)

        bind.webView.loadUrl("https://github.com/miqdad71")
        bind.webView.settings.allowContentAccess = true
        bind.webView.settings.allowFileAccess = true
        bind.webView.settings.domStorageEnabled = true
        bind.webView.webChromeClient = ChromeClient(this)
        bind.webView.webViewClient = WebClient(this)
    }

    override fun onPageStarted() {
        bind.progressBar.visibility = View.VISIBLE
        bind.webView.visibility = View.GONE
    }

    override fun onPageFinish() {
        bind.progressBar.visibility = View.GONE
        bind.webView.visibility = View.VISIBLE
    }

    override fun onShouldOverrideUrl(redirectUrl: String) {
        Log.d("msg", "redirect url: $redirectUrl")
    }

    override fun onProgressChange(progress: Int) {
        bind.progressBar.progress = progress
    }

    override fun onBackPressed() {
        if (bind.webView.canGoBack()) {
            bind.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}