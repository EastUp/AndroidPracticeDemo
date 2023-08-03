package com.east.navigation.advance.bottom_view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.east.navigation.R
import kotlinx.android.synthetic.main.fragment_register_first.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registerFirstFragment_to_registerSecondFragment))
    }


}
