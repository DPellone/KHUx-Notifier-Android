package it.lexedian.unionnotifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast

class WebActivity : AppCompatActivity() {

    lateinit var url: String
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        url = intent.getStringExtra(MESSAGE_URL)
        webView = findViewById(R.id.webView)
        webView.loadUrl(url)
    }
}
