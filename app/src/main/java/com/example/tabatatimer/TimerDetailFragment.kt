package com.example.tabatatimer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.tabatatimer.repos.TimerRepository
import com.example.tabatatimer.room.entities.Timer
import com.example.tabatatimer.viewmodels.TimerDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class TimerDetailFragment : Fragment() {

    private val viewModel: TimerDetailViewModel by activityViewModels()
    private lateinit var navBar: BottomNavigationView
    private lateinit var title: EditText
    private lateinit var preparation: EditText
    private lateinit var workout: EditText
    private lateinit var rest: EditText
    private lateinit var cycles: EditText

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer_detail, container, false)
        val id = requireArguments().getInt("id", 0)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        val button: Button = view.findViewById(R.id.timer_submit_button)
        button.setOnClickListener(::onSubmitClick)

        title = view.findViewById(R.id.timer_title_text)
        preparation = view.findViewById(R.id.timer_preparation_text)
        workout = view.findViewById(R.id.timer_workout_text)
        rest = view.findViewById(R.id.timer_rest_text)
        cycles = view.findViewById(R.id.timer_cycles_text)

        lifecycleScope.launch {
            val timer = TimerRepository.getInstance(requireContext()).get(id)
            if (timer != null) {
                viewModel.id = timer.id
                viewModel.title = timer.title
                viewModel.preparation = timer.preparation
                viewModel.workout = timer.workout
                viewModel.rest = timer.rest
                viewModel.cycles = timer.cycles
            }
            else {
                viewModel.id = id
                viewModel.title = ""
                viewModel.preparation = 0
                viewModel.workout = 1
                viewModel.rest = 0
                viewModel.cycles = 1
            }

            title.setText(viewModel.title)
            preparation.setText(viewModel.preparation.toString())
            workout.setText(viewModel.workout.toString())
            rest.setText(viewModel.rest.toString())
            cycles.setText(viewModel.cycles.toString())
        }

        title.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.title = s.toString()
            }
        })
        preparation.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    viewModel.preparation = 0
                }
                else {
                    viewModel.preparation = s.toString().toInt()
                }
            }
        })
        workout.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    viewModel.workout = 1
                }
                else {
                    viewModel.workout = s.toString().toInt()
                }
            }
        })
        rest.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    viewModel.rest = 0
                }
                else {
                    viewModel.rest = s.toString().toInt()
                }
            }
        })
        cycles.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    viewModel.cycles = 1
                }
                else {
                    viewModel.cycles = s.toString().toInt()
                }
            }
        })

        return view
    }

    override fun onDetach() {
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        super.onDetach()
    }

    private fun onSubmitClick(v: View)
    {
        if (title.text.toString() == "") {
            Toast.makeText(requireContext(), "Enter title", Toast.LENGTH_SHORT).show()
            return
        }
        val timer = Timer(
                id = viewModel.id,
                title = viewModel.title,
                preparation = viewModel.preparation,
                workout = viewModel.workout,
                rest = viewModel.rest,
                cycles = viewModel.cycles
        )
        lifecycleScope.launch {
            TimerRepository.getInstance(requireContext()).insert(timer)
        }
        requireActivity().supportFragmentManager.popBackStack();
    }

    companion object {

        fun newInstance(id: Int): TimerDetailFragment {
            val fragment = TimerDetailFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
}