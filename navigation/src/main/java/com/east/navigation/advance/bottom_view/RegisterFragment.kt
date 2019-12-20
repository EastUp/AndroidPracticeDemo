package com.east.navigation.advance.bottom_view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.east.navigation.R

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        findNavController().addOnDestinationChangedListener{controller, destination, arguments ->
//            when(destination!!.id){
//                R.id.homeFragment -> NavigationUI.setupActionBarWithNavController(
//                    activity as AppCompatActivity,
//                    Navigation.findNavController(activity!!, R.id.nav_host_home)
//                )
//                R.id.leaderBoardFragment -> NavigationUI.setupActionBarWithNavController(
//                    activity as AppCompatActivity,
//                    Navigation.findNavController(activity!!, R.id.nav_host_leaderboard)
//                )
//                R.id.registerFragment -> NavigationUI.setupActionBarWithNavController(
//                    activity as AppCompatActivity,
//                    Navigation.findNavController(activity!!, R.id.nav_host_register)
//                )
//            }
//        }

        NavigationUI.setupActionBarWithNavController(
            activity as AppCompatActivity,
            Navigation.findNavController(activity!!, R.id.nav_host_register)
        )

    }

}
