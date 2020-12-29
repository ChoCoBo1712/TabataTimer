package com.example.tabatatimer

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.tabatatimer.viewmodels.TimerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class TimerFragment : Fragment() {

    private val viewModel: TimerViewModel by activityViewModels()
    private lateinit var timer: CountDownTimer
    private lateinit var navBar: BottomNavigationView
    private lateinit var actionBar: ActionMenuItemView
    private lateinit var cycles: TextView
    private lateinit var play: ImageButton
    private lateinit var phase: TextView
    private lateinit var countdown: TextView

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

            cycles.text = resources.getString(R.string.cycles_count, viewModel.cycle, viewModel.cycles)
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
        }

        return view
    }

    private fun phaseTime(phaseInt: Int) {
        when (phaseInt) {
            0 -> {
                phase.setText(R.string.preparation)
                viewModel.time = viewModel.preparation
            }
            1 -> {
                phase.setText(R.string.workout)
                viewModel.time = viewModel.workout
            }
            2 -> {
                phase.setText(R.string.rest)
                viewModel.time = viewModel.rest
            }
        }
    }

    private fun onNextClick(v: View) {
        if (viewModel.phase < 2) {
            if (viewModel.isRunning) {
                pauseTimer()
                viewModel.phase += 1
                phaseTime(viewModel.phase)
                startTimer(viewModel.time.toLong())
            }
            else {
                viewModel.phase += 1
                phaseTime(viewModel.phase)
                countdown.text = viewModel.time.toString()
            }
        }
    }

    private fun onPrevClick(v: View) {
        if (viewModel.phase > 0) {
            if (viewModel.isRunning) {
                pauseTimer()
                viewModel.phase -= 1
                phaseTime(viewModel.phase)
                startTimer(viewModel.time.toLong())
            }
            else {
                viewModel.phase -= 1
                phaseTime(viewModel.phase)
                countdown.text = viewModel.time.toString()
            }
        }
    }

    private fun onPlayClick(v: View) {
        if (!viewModel.isRunning) {
            when (viewModel.phase) {
                0 -> {
                    phase.setText(R.string.preparation)
                }
                1 -> {
                    phase.setText(R.string.workout)
                }
                2 -> {
                    phase.setText(R.string.rest)
                }
            }
            startTimer(viewModel.time.toLong())
        }
        else {
            pauseTimer()
        }
    }

    private fun pauseTimer() {
        timer.cancel()
        viewModel.isRunning = false
        play.setImageResource(android.R.drawable.ic_media_play)
    }

    private fun startTimer(time: Long) {
        timer = object : CountDownTimer(time * 1000, 1000) {
            override fun onFinish() {
                if (viewModel.phase < 2) {
                    val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ring)
                    mediaPlayer.start()
                    viewModel.phase += 1
                    phaseTime(viewModel.phase)
                    val handler = Handler()
                    handler.postDelayed({
                        startTimer(viewModel.time.toLong())
                    }, 2000)
                }
                else {
                    if (viewModel.cycle < viewModel.cycles) {
                        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ring)
                        mediaPlayer.start()
                        viewModel.cycle += 1
                        cycles.text = resources.getString(R.string.cycles_count, viewModel.cycle, viewModel.cycles)
                        viewModel.phase = 0
                        phase.setText(R.string.preparation)
                        val handler = Handler()
                        handler.postDelayed({
                            startTimer(viewModel.time.toLong())
                        }, 2000)
                    }
                    else {
                        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.finish)
                        mediaPlayer.start()
                        viewModel.cycle = 1
                        viewModel.phase = 0
                        phase.setText(R.string.finished)
                        countdown.text = "0"
                        viewModel.isRunning = false
                        play.setImageResource(android.R.drawable.ic_media_play)
                    }
                }
            }

            override fun onTick(timeLeft: Long) {
                val left = (timeLeft / 1000) + 1
                countdown.text = left.toString()
                viewModel.time = left.toInt()
            }
        }
        timer.start()

        viewModel.isRunning = true
        play.setImageResource(android.R.drawable.ic_media_pause)
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