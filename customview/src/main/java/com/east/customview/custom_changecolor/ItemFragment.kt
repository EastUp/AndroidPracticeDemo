package com.east.customview.custom_changecolor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.east.customview.R
import kotlinx.android.synthetic.main.fragment_item.*

/**
 * Created by Darren on 2016/12/5.
 * Email: 240336124@qq.com
 * Description:
 */

class ItemFragment : Fragment() {

    val TAG = ItemFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, null)
        Log.d(TAG,"onCreateView:${arguments!!.getString("title")}")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        text.text = bundle!!.getString("title")
        Log.d(TAG,"onActivityCreated:${arguments!!.getString("title")}")
    }

    companion object {

        fun newInstance(item: String): ItemFragment {
            val itemFragment = ItemFragment()
            val bundle = Bundle()
            bundle.putString("title", item)
            itemFragment.arguments = bundle
            return itemFragment
        }
    }



    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume:${arguments!!.getString("title")}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart:${arguments!!.getString("title")}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause:${arguments!!.getString("title")}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop:${arguments!!.getString("title")}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG,"onDestroyView:${arguments!!.getString("title")}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy:${arguments!!.getString("title")}")
    }

}
