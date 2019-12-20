package com.east.architecture_components.viewmodel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.east.architecture_components.R
import com.east.architecture_components.viewmodel.ShareViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Test1Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Test1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Test1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val shareViewModel = ViewModelProviders.of(activity!!).get(ShareViewModel::class.java)
        shareViewModel.data.value = "Test1Fragment"
    }

}
