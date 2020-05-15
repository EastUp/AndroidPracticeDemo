package com.east.architect_zenghui.architect9_designmode2_builder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect9_designmode2_builder.navigation.DefaultNavigationBar
import kotlinx.android.synthetic.main.activity_builder.*

class BuilderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)


        // connectTimeout().addNetworkInterceptor().addInterceptor() 其实是要给OKHttp用的
        // 按照一般的套路基本都是 OkHttpClient client = new OkHttpClient();
        //                        client.setTimeOut();
        //                        client.setNetworkInterceptor();
        //                        client.setInterceptor();
        /*OkHttpClient client = new OkHttpClient.Builder().connectTimeout()
                .addNetworkInterceptor().addInterceptor().build();*/


//        NavigationBar.Builder(this,R.layout.ui_navigation_bar,view_root)
//            .setText(R.id.back_tv,"返回")
//            .setOnClickListener(R.id.back_tv){
//                finish()
//            }.create()


        DefaultNavigationBar.Builder(this)
            .setLeftText("Back")
            .setLeftClickListener {
                finish()
            }
            .create()

    }
}
