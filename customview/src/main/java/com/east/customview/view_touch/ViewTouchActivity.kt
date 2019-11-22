package com.east.customview.view_touch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_view_touch.*

class ViewTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_touch)


        /**
         *  如果返回true,就不会调用到View的onTouchEvent中
         *
         *  if (onFilterTouchEventForSecurity(event)) {
         *      if ((mViewFlags & ENABLED_MASK) == ENABLED && handleScrollBarDragging(event)) {
         *          result = true;
         *      }
         *      //noinspection SimplifiableIfStatement
         *   ListenerInfo li = mListenerInfo;
         *      if (li != null && li.mOnTouchListener != null
         *      && (mViewFlags & ENABLED_MASK) == ENABLED
         *      && li.mOnTouchListener.onTouch(this, event)) {
         *      result = true;
         *  }
         *
         *      if (!result && onTouchEvent(event)) {
         *      result = true;
         *      }
         *  }
         *
         */
        touchView.setOnTouchListener { v, event ->
            Log.e("TAG","View -> OnTouchListener")
            false
        }


        /**
         *  在View的onTouchEvent中调用的 performClickInternal();
         *  设置了点击事件super.onTouchEvent才会返回true
         */
        touchView.setOnClickListener {
            Log.e("TAG","View -> OnClickListener")
        }

    }
}
