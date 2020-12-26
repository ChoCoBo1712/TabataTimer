package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner


class SequenceDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sequence_detail, container, false)

        val colour: Spinner = view.findViewById<Spinner>(R.id.sequence_colour)
        val adapter = ArrayAdapter.createFromResource(requireActivity().applicationContext, R.array.colours, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colour.adapter = adapter


        return view
    }

    companion object {

        fun newInstance() : SequenceDetailFragment {
            return SequenceDetailFragment()
        }
    }
}