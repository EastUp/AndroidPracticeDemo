package com.east.navigation.advance.bottom_view


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.east.navigation.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val TAG = HomeFragment::class.java.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
//        findNavController().addOnDestinationChangedListener{controller, destination, arguments ->
//            when(destination!!.id){
//                R.id.homeFragment -> setupActionBarWithNavController(activity as AppCompatActivity, Navigation.findNavController(activity!!,R.id.nav_host_home))
//                R.id.leaderBoardFragment -> setupActionBarWithNavController(activity as AppCompatActivity, Navigation.findNavController(activity!!,R.id.nav_host_leaderboard))
//                R.id.registerFragment -> setupActionBarWithNavController(activity as AppCompatActivity, Navigation.findNavController(activity!!,R.id.nav_host_register))
//            }
//        }

        val navController = Navigation.findNavController(activity!!, R.id.nav_host_home)
        NavigationUI.setupActionBarWithNavController(
            activity as AppCompatActivity,
            navController
        )
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")

//        val navController = Navigation.findNavController(activity!!, R.id.nav_host_home)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }


}
