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
import com.example.gamershub.databinding.FragmentGametrackerlistBinding
import com.example.gamershub.model.GameResult
import com.example.gamershub.ui.adapter.GameTrackerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GameTrackerListFragment: Fragment() {
    private lateinit var binding: FragmentGametrackerlistBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adapter: GameTrackerAdapter
    private var games: MutableList<GameResult> = mutableListOf()
    private lateinit var estado: String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        estado = arguments?.getString("estado") ?: "QuieroJugar"
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
        adapter = GameTrackerAdapter(games, requireContext(), estado) { game ->
            navegarADetalleJuego(game)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGametrackerlistBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.recyclerViewGamesTracker.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewGamesTracker.adapter = adapter
        binding.toolbarGameTracker.inflateMenu(R.menu.menu_mygames)
        binding.toolbarGameTracker.overflowIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbarGameTracker.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.volverGameTracker -> {
                    findNavController().navigate(R.id.action_gameTrackerListFragment_to_gameTrackerFragment)
                    return@setOnMenuItemClickListener true
                }

            }
            return@setOnMenuItemClickListener true
        }
        cargarJuegosDesdeFirebase()
    }


    private fun navegarADetalleJuego(game: GameResult) {
        val bundle = Bundle()
        game.id?.let { bundle.putLong("game_id", it) }
        findNavController().navigate(R.id.action_gameTrackerListFragment_to_detailsGameFragment, bundle)

    }
    private fun cargarJuegosDesdeFirebase() {
        val currentUser = auth.currentUser ?: return
        val reference = database.reference
            .child("users")
            .child(currentUser.uid)
            .child("GameTracker")
            .child(estado) // categoría específica

        reference.get().addOnSuccessListener { snapshot ->
            games.clear()
            for (child in snapshot.children) {
                val juego = child.getValue(GameResult::class.java)
                juego?.let { games.add(it) }
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            // Manejo de errores opcional
        }
    }



}