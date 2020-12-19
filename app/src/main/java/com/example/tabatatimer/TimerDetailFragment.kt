package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimerDetailFragment : Fragment() {

    private val viewModel: TimerDetailFragment by activityViewModels()
    private lateinit var navBar: BottomNavigationView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        return inflater.inflate(R.layout.fragment_timer_detail, container, false)
    }

    override fun onDetach() {
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        super.onDetach()
    }

    companion object {

        fun newInstance(): TimerDetailFragment {
            return TimerDetailFragment()
        }
    }
}