package com.example.tabatatimer

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData

import com.example.tabatatimer.dummy.DummyContent.DummyItem
import com.example.tabatatimer.room.entities.Timer
import java.sql.Time

class TimerListAdapter() : RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    var values: List<Timer>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
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
            holder.title.text = item.title
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

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }
}