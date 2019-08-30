package com.east.navigation.basic


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.east.navigation.BlankFragmentArgs
import com.east.navigation.BlankFragmentDirections
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_blank.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {


    //安全的获取传递的data数据,fromBundle(),kotlin可以使用下面封装后的方法
    val args : BlankFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action =
            BlankFragmentDirections.actionBlankFragmentToBlankFragment2(
                "BlankFragment action传递的数据:age"
            )

        button.setOnClickListener {
//            it.findNavController().navigate(R.id.action_blankFragment_to_blankFragment2)
            it.findNavController().navigate(action)
        }

        back.setOnClickListener {
//            it.findNavController().navigateUp()
            it.findNavController().popBackStack(R.id.blankFragment2,false)
        }

        //接受数据
        tv_data.text = args.name
//        tv_data.text = BlankFragmentArgs.fromBundle(arguments!!).name
//        tv_data.text = arguments?.getString("name")

    }




}
