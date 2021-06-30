package com.east.connotationjokes

import android.app.Application
import android.os.Environment
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.alipay.euler.andfix.patch.PatchManager
import com.east.baselibrary.fixbug.FixDexManager
import com.east.baselibrary.headerbar.CommonHeaderBar
import com.east.baselibrary.http.HttpUtils
import com.east.baselibrary.utils.crash_handler.ExceptionCrashHandler
import com.east.east_utils.utils.SPUtils
import com.east.east_utils.utils.Utils
import com.east.framelibrary.http.OkHttpEngine
import com.east.framelibrary.skin.ResourcesManager
import com.east.framelibrary.skin.SkinManager
import com.east.framelibrary.skin.attr.SkinView
import com.east.framelibrary.skin.support.ChangeSkinAttrs
import java.io.File

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/5/6
 * |---------------------------------------------------------------------------------------------------------------|
 */
class BaseApplication : Application() {

    companion object {
        var mPatchManager: PatchManager? = null
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        SPUtils.init(this)
        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance().init(this)
        CommonHeaderBar.mDefaultLayoutId = R.layout.title_bar;
        HttpUtils.init(OkHttpEngine())
        SkinManager.getInstance().init(this)


        SkinManager.getInstance().setJudgeViewAttributeNameListener {
            TextUtils.equals(it, ChangeSkinAttrs.BACKGROUND.attrName) ||
                    TextUtils.equals(it, ChangeSkinAttrs.TEXTCOLOR.attrName) ||
                    TextUtils.equals(it, ChangeSkinAttrs.SRC.attrName)
        }.setSkinChangeListener { originalResource, skinView ->
            skin(originalResource,skinView)
        }

//        //初始化阿里热修复
//        mPatchManager = PatchManager(this)
//        val packageInfo = packageManager.getPackageInfo(packageName, 0)
//        val versionName = packageInfo.versionName
//        mPatchManager!!.init(versionName)
//
//        //加载之前的apatch包
//        mPatchManager!!.loadPatch()

        var fixDexManager = FixDexManager(this)
        fixDexManager.loadAllFixDex() //加载所有的包
        val fixFile =
            File(Environment.getExternalStorageDirectory(), "fix.dex")
        if (fixFile.exists())
            fixDexManager.fixDex(fixFile.absolutePath)

    }


    /**
     * @param original 是否换回系统原来的皮肤
     */
    fun skin(original: Boolean,skinView: SkinView) {
        var skinAttrAndResourceNames = skinView.skinAttrAndResourceNames;
        var view = skinView.view
        for (skinAttrAndResourceName in skinAttrAndResourceNames) {
            val name = skinAttrAndResourceName.attributeName
            val resourceName = skinAttrAndResourceName.resourceName
            //根据不同的属性名称为view设置不同的资源
            if (name == ChangeSkinAttrs.TEXTCOLOR.attrName) {
                val colors =
                    ResourcesManager.getInstance().getColorByName(resourceName, original)
                        ?: return
                if (view is TextView) {
                    val textView = view as TextView
                    textView.setTextColor(colors)
                }
            } else if (name == ChangeSkinAttrs.BACKGROUND.attrName) {
                //背景有可能是图片
                val drawable =
                    ResourcesManager.getInstance().getDrawableByName(resourceName, original)
                if (drawable != null) {
                    view.setBackground(drawable)
                }
                //也有可能是颜色
                val colors =
                    ResourcesManager.getInstance().getColorByName(resourceName, original)
                        ?: return
                view.setBackgroundColor(colors.defaultColor)
            } else if (name == ChangeSkinAttrs.SRC.attrName) {
                val drawable =
                    ResourcesManager.getInstance().getDrawableByName(resourceName, original)
                        ?: return
                if (view is ImageView) {
                    val imageView = view as ImageView
                    imageView.setImageDrawable(drawable)
                }
            }
        }
    }

}