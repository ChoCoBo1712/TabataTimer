package com.example.tabatatimer

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.tabatatimer.viewmodels.TimerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {

    private val viewModel: TimerViewModel by activityViewModels()
    private lateinit var timer: CountDownTimer
    private lateinit var navBar: BottomNavigationView
    private lateinit var actionBar: ActionMenuItemView
    private lateinit var cycles: TextView
    private lateinit var play: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_timer, container, false)
        val id = requireArguments().getInt("id", 0)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE
        actionBar = requireActivity().findViewById(R.id.action_add)
        actionBar.visibility = View.GONE

        cycles = view.findViewById(R.id.timer_cycles)
        val title: TextView = view.findViewById(R.id.timer_title)
        play = view.findViewById(R.id.timer_play)
        play.setOnClickListener(::onPlayClick)

        lifecycleScope.launch {
            val sequence = SequenceRepository.getInstance(requireContext()).get(id)

            if (sequence != null) {
                viewModel.title = sequence.title
                viewModel.preparation = sequence.preparation
                viewModel.workout = sequence.workout
                viewModel.rest = sequence.rest
                viewModel.phase = 0
                viewModel.cycle = 1
                viewModel.cycles = sequence.cycles
            }
            else {
                requireActivity().supportFragmentManager.popBackStack()
            }

            cycles.text = resources.getString(R.string.cycles_count, 1, viewModel.cycles)
            title.text = viewModel.title
        }

        return view
    }

    private fun onPlayClick(v: View) {
        if (!viewModel.isRunning) {
            play.setImageResource(android.R.drawable.ic_media_pause)
            viewModel.isRunning = true
        }
        else {
            play.setImageResource(android.R.drawable.ic_media_play)
            viewModel.isRunning = false
        }
    }

    override fun onDetach() {
        navBar.visibility = View.VISIBLE
        actionBar.visibility = View.VISIBLE

        super.onDetach()
    }

    companion object {

        fun newInstance(id: Int): TimerFragment {
            val fragment = TimerFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
}