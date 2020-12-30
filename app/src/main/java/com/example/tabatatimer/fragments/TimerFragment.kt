package com.example.tabatatimer.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.tabatatimer.R
import com.example.tabatatimer.SequenceRepository
import com.example.tabatatimer.TimerService
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
    private lateinit var phase: TextView
    private lateinit var countdown: TextView
    lateinit var receiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_timer, container, false)
        val id = requireArguments().getInt("id", 0)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE
        actionBar = requireActivity().findViewById(R.id.action_add)
        actionBar.visibility = View.GONE

        cycles = view.findViewById(R.id.timer_cycles)
        val title: TextView = view.findViewById(R.id.timer_title)
        play = view.findViewById(R.id.timer_play)
        val next: ImageButton = view.findViewById(R.id.timer_next)
        val prev: ImageButton = view.findViewById(R.id.timer_prev)
        phase = view.findViewById(R.id.timer_phase)
        countdown = view.findViewById(R.id.timer_countdown)

        play.setOnClickListener(::onPlayClick)
        next.setOnClickListener(::onNextClick)
        prev.setOnClickListener(::onPrevClick)

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
                viewModel.time = viewModel.preparation
                viewModel.colour = sequence.colour
            }
            else {
                requireActivity().supportFragmentManager.popBackStack()
            }

            cycles.text = resources.getString(
                R.string.cycles_count,
                viewModel.cycle,
                viewModel.cycles
            )
            title.text = viewModel.title
            countdown.text = viewModel.time.toString()
            when(viewModel.colour) {
                0 -> {
                    view.setBackgroundColor(Color.BLUE)
                }
                1 -> {
                    view.setBackgroundColor(Color.RED)
                }
                2 -> {
                    view.setBackgroundColor(Color.GREEN)
                }
            }

            val filter = IntentFilter()
            filter.addAction("message")
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent != null) {
                        val message = intent.getStringExtra("message")
                        when (message) {
                            "tick" -> {
                                countdown.text = intent.getLongExtra("left", 1).toString()
                            }
                            "phase" -> {
                                phase.setText(intent.getIntExtra("phase", 0))
                            }
                            "cycles" -> {
                                cycles.text = resources.getString(
                                    R.string.cycles_count,
                                        intent.getIntExtra("cycle", 1),
                                        viewModel.cycles
                                )
                                phase.setText(R.string.preparation)
                            }
                            "finished" -> {
                                phase.setText(R.string.finished)
                                play.setImageResource(android.R.drawable.ic_media_play)
                                countdown.text = "0"
                            }
                            "arrow" -> {
                                countdown.text = intent.getIntExtra("counter", 1).toString()
                            }
                            "playPause" -> {
                                when {
                                    intent.getStringExtra("image") == "play" -> {
                                        play.setImageResource(android.R.drawable.ic_media_play)
                                    }
                                    else -> {
                                        play.setImageResource(android.R.drawable.ic_media_pause)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            requireActivity().registerReceiver(receiver, filter)

            Intent(requireContext(), TimerService::class.java).also {
                val bundle = Bundle()
                bundle.putInt("preparation", viewModel.preparation)
                bundle.putInt("workout", viewModel.workout)
                bundle.putInt("rest", viewModel.rest)
                bundle.putInt("cycles", viewModel.cycles)
                it.putExtras(bundle)
                requireContext().startService(it)
            }
        }

        return view
    }

    private fun onNextClick(v: View) {
        Intent(requireContext(), TimerService::class.java).also {
            val bundle = Bundle()
            bundle.putString("command", "next")
            it.putExtras(bundle)
            requireContext().startService(it)
        }
    }

    private fun onPrevClick(v: View) {
        Intent(requireContext(), TimerService::class.java).also {
            val bundle = Bundle()
            bundle.putString("command", "prev")
            it.putExtras(bundle)
            requireContext().startService(it)
        }
    }

    private fun onPlayClick(v: View) {
        if (phase.text == resources.getString(R.string.finished)) {
            phase.setText(R.string.preparation)
            cycles.text = resources.getString(
                R.string.cycles_count,
                    1,
                    viewModel.cycles
            )
        }
        Intent(requireContext(), TimerService::class.java).also {
            val bundle = Bundle()
            bundle.putString("command", "playPause")
            it.putExtras(bundle)
            requireContext().startService(it)
        }
    }

    override fun onDetach() {
        navBar.visibility = View.VISIBLE
        actionBar.visibility = View.VISIBLE

        Intent(requireContext(), TimerService::class.java).also {
            requireContext().stopService(it)
        }

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)

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