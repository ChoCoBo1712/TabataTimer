package com.example.tabatatimer

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabatatimer.repos.TimerRepository
import com.example.tabatatimer.room.entities.Timer
import kotlinx.coroutines.launch

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

        timerListAdapter.onItemClick = { timer: Timer, itemView: View ->
            val popup = PopupMenu(requireContext(), itemView)
            val menuInflater: MenuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.item_popup, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_start -> {
                        //TODO
                        true
                    }
                    R.id.item_edit -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, TimerDetailFragment.newInstance(timer.id))
                                .addToBackStack(null)
                                .commit()
                        true
                    }
                    R.id.item_delete -> {
                        lifecycleScope.launch {
                            TimerRepository.getInstance(requireContext()).delete(timer)
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        return view
    }

    companion object {

        fun newInstance(): TimerListFragment {
            return TimerListFragment()
        }
    }
}