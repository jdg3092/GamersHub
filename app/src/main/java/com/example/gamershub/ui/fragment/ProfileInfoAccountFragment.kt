package com.example.gamershub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamershub.databinding.FragmentProfileinfoaccountBinding

class ProfileInfoAccountFragment: Fragment() {
    private lateinit var binding: FragmentProfileinfoaccountBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileinfoaccountBinding.inflate(inflater, container, false)
        return binding.root
    }


}