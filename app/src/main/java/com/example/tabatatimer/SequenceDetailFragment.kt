package com.example.tabatatimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SequenceDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sequence_detail, container, false)
    }

    companion object {

        fun newInstance() : SequenceDetailFragment {
            return SequenceDetailFragment()
        }
    }
}