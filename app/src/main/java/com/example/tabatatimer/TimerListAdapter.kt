package com.example.tabatatimer

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.tabatatimer.interfaces.OnItemClickListener

import com.example.tabatatimer.room.entities.Timer
import java.sql.Time

class TimerListAdapter() : RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    var values: List<Timer>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_timer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (values != null)
        {
            val item = values!![position]
            with(holder) {
                title.text = item.title
                preparation.text = item.preparation.toString()
                workout.text = item.workout.toString()
                rest.text = item.rest.toString()
                cycles.text = item.cycles.toString()
                itemView.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (values != null)
        {
            return values!!.size
        }
        return 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.adapter_title)
        val preparation: TextView = view.findViewById(R.id.adapter_preparation)
        val workout: TextView = view.findViewById(R.id.adapter_workout)
        val rest: TextView = view.findViewById(R.id.adapter_rest)
        val cycles: TextView = view.findViewById(R.id.adapter_cycles)

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }
}