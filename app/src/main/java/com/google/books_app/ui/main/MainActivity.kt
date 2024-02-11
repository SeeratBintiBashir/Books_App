package com.google.books_app.ui.main

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.books_app.database.BooksDatabaseHelper
import com.google.books_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BooksDatabaseHelper
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.myWebView.getSettings().setJavaScriptEnabled(true);
        val webSettings: WebSettings = binding.myWebView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true // Enable DOM Storage

        webSettings.databaseEnabled = true // Enable Database Storage


        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        binding.myWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // Get the loaded HTML content
                val loadedHtml = view.url

                view.loadUrl("javascript:window.Android.getContent(document.getElementsByTagName('html')[0].innerHTML);");
                Log.e("HTML",""+loadedHtml)
                // Now 'loadedHtml' contains the HTML content of the loaded page
            }
        })
       binding.myWebView.addJavascriptInterface( MyJavaScriptInterface(), "Android")
        // Load a URL into the WebView
        val headers: MutableMap<String, String> = HashMap()
        headers["User-Agent"] =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
        // Load a URL into the WebView
       binding.myWebView.loadUrl("https://blinkit.com/",headers)


    }
    private class MyJavaScriptInterface {
        @JavascriptInterface
        fun getContent(htmlContent: String?) {
            // Now 'htmlContent' contains the HTML content of the loaded page
            // You can use this content as needed
            Log.e("HTML",htmlContent?:"")
         //   doHTMLProcess(htmlContent?:"")


        }


    }
    override fun onResume() {
        super.onResume()

    }


}