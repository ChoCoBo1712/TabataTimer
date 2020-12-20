package com.example.tabatatimer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabatatimer.room.entities.Timer

class TimerListAdapter() : RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    var timers: List<Timer>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemClick: ((Timer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_timer, parent, false)
        val viewHolder = ViewHolder(view)

        if (timers != null) {
            viewHolder.itemView.setOnClickListener {
                onItemClick?.invoke(timers!![viewHolder.adapterPosition])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (timers != null)
        {
            val item = timers!![position]
            with(holder) {
                title.text = item.title
                preparation.text = item.preparation.toString()
                workout.text = item.workout.toString()
                rest.text = item.rest.toString()
                cycles.text = item.cycles.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        if (timers != null)
        {
            return timers!!.size
        }
        return 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.adapter_title)
        val preparation: TextView = view.findViewById(R.id.adapter_preparation)
        val workout: TextView = view.findViewById(R.id.adapter_workout)
        val rest: TextView = view.findViewById(R.id.adapter_rest)
        val cycles: TextView = view.findViewById(R.id.adapter_cycles)
    }
}