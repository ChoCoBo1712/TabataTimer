package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimerDetailFragment : Fragment() {

    private val viewModel: TimerDetailFragment by activityViewModels()
    private lateinit var navBar: BottomNavigationView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer_detail, container, false)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        val button: Button = view.findViewById(R.id.timer_submit_button)
        button.setOnClickListener(::onSubmitClick)

        return view
    }

    override fun onDetach() {
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        super.onDetach()
    }

    private fun onSubmitClick(v: View)
    {
        Toast.makeText(requireContext(), "hey", Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): TimerDetailFragment {
            return TimerDetailFragment()
        }
    }
}