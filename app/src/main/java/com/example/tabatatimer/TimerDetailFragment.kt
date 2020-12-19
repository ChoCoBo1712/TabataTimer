package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

class TimerDetailFragment : Fragment() {

    private val viewModel: TimerDetailFragment by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_timer_detail, container, false)
    }

    companion object {

        fun newInstance(): TimerDetailFragment {
            return TimerDetailFragment()
        }
    }
}