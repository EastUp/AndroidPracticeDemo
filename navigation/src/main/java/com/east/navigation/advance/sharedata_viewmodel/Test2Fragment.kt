package com.east.navigation.advance.sharedata_viewmodel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_test2.*

/**
 * A simple [Fragment] subclass.
 */
class Test2Fragment : Fragment() {

//    val shareViewModel by navGraphViewModels<ShareViewModel>(R.navigation.nav_graph_viewmodel)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shareViewModel = ViewModelProvider(activity!!,ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(ShareViewModel::class.java)

        shareViewModel.data.observe(this, Observer {
            tv_f2.text = it
        })

    }


}
