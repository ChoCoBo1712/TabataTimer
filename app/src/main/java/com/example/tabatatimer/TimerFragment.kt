package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimerFragment : Fragment() {

    private lateinit var navBar: BottomNavigationView
    private lateinit var actionBar: ActionMenuItemView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_timer, container, false)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE
        actionBar = requireActivity().findViewById(R.id.action_add)
        actionBar.visibility = View.GONE

        return view
    }

    override fun onDetach() {
        navBar.visibility = View.VISIBLE
        actionBar.visibility = View.VISIBLE

        super.onDetach()
    }

    companion object {

        fun newInstance(): TimerFragment {
            return TimerFragment()
        }
    }
}