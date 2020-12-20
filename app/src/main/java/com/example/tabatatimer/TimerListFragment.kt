package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.tabatatimer.interfaces.OnItemClickListener
import com.example.tabatatimer.repos.TimerRepository

class TimerListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer_list, container, false)
        val timerListAdapter = TimerListAdapter()

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = timerListAdapter
            }
            view.addItemDecoration(DividerItemDecoration(view.context, 1))
        }

        timerListAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(item: Any) {

            }

        })

        TimerRepository.getInstance(requireContext()).getAll().observe(viewLifecycleOwner, { newValue ->
            timerListAdapter.values = newValue
        })

        return view
    }

    private fun onItemClick()
    {

    }

    companion object {

        fun newInstance(): TimerListFragment {
            return TimerListFragment()
        }
    }
}