package com.east.architect_zenghui.architect_32_rxjava4.rxlogin.rxlogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA

class RxLoginActivity : AppCompatActivity(), UMAuthListener {

    private lateinit var mUmShareAPI: UMShareAPI

    companion object {
        const val PLATFORM_KEY = "PLATFORM_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUmShareAPI = UMShareAPI.get(this)
        val platform = intent.getSerializableExtra(PLATFORM_KEY) as RxLoginPlatform
        mUmShareAPI.deleteOauth(this, platformChange(platform), null)
        mUmShareAPI.getPlatformInfo(this, platformChange(platform), this)

    }

    /**
     * 平台转换
     * @param platform
     * @return
     */
    private fun platformChange(platform: RxLoginPlatform): SHARE_MEDIA? {
        when (platform) {
            RxLoginPlatform.Platform_QQ -> return SHARE_MEDIA.QQ
            RxLoginPlatform.Platform_WX -> return SHARE_MEDIA.WEIXIN
        }
        return SHARE_MEDIA.QQ
    }

    override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>?) {
        RxLogin.STATIC_LISTENER.onComplete(p0, p1, p2)
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
        RxLogin.STATIC_LISTENER.onCancel(p0, p1)
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
        RxLogin.STATIC_LISTENER.onError(p0, p1, p2)
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        RxLogin.STATIC_LISTENER.onStart(p0)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
