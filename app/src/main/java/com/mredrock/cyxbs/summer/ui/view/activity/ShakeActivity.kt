package com.mredrock.cyxbs.summer.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.webkit.*
import com.mredrock.cyxbs.summer.R
import com.mredrock.cyxbs.summer.ui.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_shake.*
import kotlinx.android.synthetic.main.summer_include_toolbar.*

class ShakeActivity : AppCompatActivity() {
    lateinit var url:String

    @SuppressLint("JavascriptInterface", "CheckResult", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        summer_include_tl.setNavigationOnClickListener {
            finish()
        }
        summer_include_tv.text = "摇一摇"
        url = SearchFragment.url
        wv_redPage.loadUrl(url)
        val webSettings = wv_redPage.settings
        webSettings.apply {
            javaScriptEnabled = true//支持js
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK//不使用缓存
            javaScriptCanOpenWindowsAutomatically = true
        }

        wv_redPage.apply {
            addJavascriptInterface(this,"android")
            webChromeClient = mWebChromeClient
            webViewClient = mWebViewClient
        }
    }
    private val mWebViewClient = object : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            try{


            }catch (e : Exception){
                return false
            }
            wv_redPage.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            pb_redPage.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            pb_redPage.visibility = View.GONE
        }
    }

    private val mWebChromeClient = object : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            pb_redPage.progress = newProgress
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK && wv_redPage.canGoBack()) {
            wv_redPage.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy(){
        super.onDestroy()
        wv_redPage.destroy()
    }
}
