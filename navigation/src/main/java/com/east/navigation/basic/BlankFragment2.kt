package com.east.navigation.basic


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.east.navigation.BlankFragment2Args
import com.east.navigation.BlankFragment2Directions
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_blank_fragment2.*

/**
 *
 */
class BlankFragment2 : Fragment() {

    //安全的获取传递的data数据,fromBundle
    val args : BlankFragment2Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val action =
//            NavGraphDirections.actionGlobalBlankFragment3("这是BlankFragment2发送的数据:height")
        val action =
            BlankFragment2Directions.actionGlobalBlankFragment3("这是BlankFragment2发送的数据:height")
        button.setOnClickListener {
            it.findNavController().navigate(action)
        }

        //接受数据
        tv_data.text = args.age

    }

}
