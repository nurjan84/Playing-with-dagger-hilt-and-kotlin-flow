package com.example.playingwithcoroutineflow.dev

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.playingwithcoroutineflow.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment() : Fragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{bundle ->
            textView.text =  InfoFragmentArgs.fromBundle(bundle).name
        }
    }

}