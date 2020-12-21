package com.example.tabatatimer

import android.os.Bundle
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
                viewModel.title.value = timer.title
                viewModel.preparation.value = timer.preparation
                viewModel.workout.value = timer.workout
                viewModel.rest.value = timer.rest
                viewModel.cycles.value = timer.cycles
            }
        }

        viewModel.title.observe(viewLifecycleOwner, { newValue -> title.setText(newValue) })
        viewModel.preparation.observe(viewLifecycleOwner, { newValue -> preparation.setText(newValue.toString()) })
        viewModel.workout.observe(viewLifecycleOwner, { newValue -> workout.setText(newValue.toString()) })
        viewModel.rest.observe(viewLifecycleOwner, { newValue -> rest.setText(newValue.toString()) })
        viewModel.cycles.observe(viewLifecycleOwner, { newValue -> cycles.setText(newValue.toString()) })

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
                title = title.text.toString(),
                preparation = preparation.text.toString().toInt(),
                workout = workout.text.toString().toInt(),
                rest = rest.text.toString().toInt(),
                cycles = cycles.text.toString().toInt()
        )
        lifecycleScope.launch {
            TimerRepository.getInstance(requireContext()).insert(timer)
        }
        requireActivity().getSupportFragmentManager().popBackStack();
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