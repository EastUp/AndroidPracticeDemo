package com.east.navigation.advance.sharedata_viewmodel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_test1.*

/**
 * A simple [Fragment] subclass.
 */
class Test1Fragment : Fragment() {

//    val shareViewModel : ShareViewModel by navGraphViewModels(R.navigation.nav_graph_viewmodel)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        shareViewModel.data.observe(this, Observer {
//            tv_f1.text = it
//        })
//
//        shareViewModel.data.value = "fragment1传递的数据"

        btn_navigation.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_test1Fragment_to_test2Fragment))


    }


}
