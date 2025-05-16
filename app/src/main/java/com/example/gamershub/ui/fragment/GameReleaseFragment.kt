package com.example.gamershub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamershub.databinding.FragmentGamereleaseBinding

class GameReleaseFragment: Fragment() {
    private lateinit var binding: FragmentGamereleaseBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamereleaseBinding.inflate(inflater, container, false)
        return binding.root
    }
}