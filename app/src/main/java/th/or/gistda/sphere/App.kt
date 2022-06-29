package th.or.gistda.sphere

import android.app.Application
import android.webkit.WebView

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupWebView()
    }

    private fun setupWebView() {
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }

}