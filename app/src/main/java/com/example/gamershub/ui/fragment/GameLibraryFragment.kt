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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentGamelibraryBinding
import com.example.gamershub.model.Game
import com.example.gamershub.model.GameResult
import com.example.gamershub.ui.adapter.GameLibraryAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class GameLibraryFragment : Fragment() {
    private lateinit var binding: FragmentGamelibraryBinding
    private lateinit var adapter: GameLibraryAdapter
    private var games: MutableList<GameResult> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = GameLibraryAdapter(games, requireContext()) { game ->
            navegarADetalleJuego(game)
        }
    }

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
        binding.recyclerViewGames.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewGames.adapter = adapter
        cargarJuegosDesdeAPI()
    }
    private fun cargarJuegosDesdeAPI() {
        val url = "https://api.rawg.io/api/games?key=8f1d2939357d42f7af531dc43d0e2172&page_size=50" // 50 juegos
        val gson = Gson()
        val request = JsonObjectRequest(url, { response ->
            val gameData = gson.fromJson(response.toString(), Game::class.java)
            games.clear()
            gameData.results?.let { games.addAll(it) }
            adapter.notifyDataSetChanged()
        }, {
            Snackbar.make(binding.root, "Error al cargar datos", Snackbar.LENGTH_SHORT)
                .show()
            it.printStackTrace()
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }
    private fun navegarADetalleJuego(game: GameResult) {
        val bundle = Bundle()
        game.id?.let { bundle.putLong("game_id", it) }
        findNavController().navigate(R.id.action_gameLibraryFragment_to_detailsGameFragment, bundle)
    }


}