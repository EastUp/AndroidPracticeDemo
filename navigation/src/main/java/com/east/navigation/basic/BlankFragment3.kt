package com.east.navigation.basic


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_blank_fragment3.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment3 : Fragment() {

    val args : BlankFragment3Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //发送数据
        val bundle = bundleOf("name" to "BlankFragment3 send data :name")

        button.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragment3_to_blankFragment,bundle)
        }

        tv_data.text = args.height

    }


}
