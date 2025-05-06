package com.example.gamershub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentGamelibraryBinding

class GameLibraryFragment : Fragment() {
    private lateinit var binding: FragmentGamelibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamelibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.toolbarGameLibrary.inflateMenu(R.menu.menu_gamelibrary)
        binding.toolbarGameLibrary.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.toolbarGameLibrary.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.volverMainFragmnet -> {
                    findNavController().navigate(R.id.action_gameLibraryFragment_to_mainFragment)
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true

        }
    }
}