package com.example.tabatatimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabatatimer.repos.TimerRepository

class TimerListFragment : Fragment() {

    private lateinit var timerListAdapter: TimerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer_list, container, false)
        timerListAdapter = TimerListAdapter()

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = timerListAdapter
            }
            view.addItemDecoration(DividerItemDecoration(view.context, 1))
        }

        TimerRepository.getInstance(requireContext()).getAll().observe(viewLifecycleOwner, { newValue ->
            timerListAdapter.timers = newValue
        })

        timerListAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }

        return view
    }

    companion object {

        fun newInstance(): TimerListFragment {
            return TimerListFragment()
        }
    }
}