package com.example.gamershub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.toolbarMain.inflateMenu(R.menu.menu_main)
        binding.toolbarMain.overflowIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbarMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.verPerfilInfo -> {
                    findNavController().navigate(R.id.action_mainFragment_to_profileInfoAccountFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.cerrarSesion -> {
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true

        }
    }
}