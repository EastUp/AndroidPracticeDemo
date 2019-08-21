package com.east.arouterpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_test_web.*

@Route(path = "/arouter1/webview")
class TestWebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_web)

        web_view.loadUrl(intent.getStringExtra("url"))
    }
}
