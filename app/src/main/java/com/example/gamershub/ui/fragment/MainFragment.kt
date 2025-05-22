package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentMainBinding
import com.example.gamershub.model.MainOption
import com.example.gamershub.ui.adapter.MainOptionAdapter
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainOptionAdapter
    private lateinit var options: List<MainOption>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        options = listOf(
            MainOption(
                "Game Library",
                R.drawable.ic_game_library,
                R.id.action_mainFragment_to_gameLibraryFragment,
                R.drawable.bg_game_library
            ),
            MainOption(
                "Game Releases",
                R.drawable.ic_game_releases,
                R.id.action_mainFragment_to_gameReleaseFragment,
                R.drawable.bg_game_release
            ),
            MainOption(
                "Game Tracker",
                R.drawable.ic_game_tracker,
                R.id.action_mainFragment_to_gameTrackerFragment,
                R.drawable.bg_game_tracker
            ),
            MainOption(
                "Forum",
                R.drawable.ic_forum,
                R.id.action_mainFragment_to_forumFragment,
                R.drawable.bg_forum
            )
        )
        adapter = MainOptionAdapter(requireContext(), options) { option ->
            findNavController().navigate(option.navActionId)
        }
    }

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
        binding.recyclerMain.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMain.adapter = adapter
        binding.toolbarMain.inflateMenu(R.menu.menu_main)
        binding.toolbarMain.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
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
