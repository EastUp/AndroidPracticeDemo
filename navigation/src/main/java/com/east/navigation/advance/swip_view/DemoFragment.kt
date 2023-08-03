package com.east.navigation.advance.swip_view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_demo.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DemoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DemoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DemoFragment : Fragment() {
    val TAG = DemoFragment::class.java.simpleName

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_info.text = param1
    }


    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DemoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }


    override fun onResume() {
        super.onResume()

    }

}
