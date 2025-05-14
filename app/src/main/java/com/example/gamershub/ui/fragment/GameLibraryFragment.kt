package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    private var allGames: MutableList<GameResult> = mutableListOf()

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
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                Log.d("DEBUG", "Texto buscado: '$searchText'")
                if (searchText.isEmpty()) {
                    // Restaurar juegos originales si no hay texto
                    Log.d("DEBUG", "Buscador vaco, restaurando juegos originales")
                    Log.d("DEBUG1", "Cantidad de juegos en allGames: ${allGames.size}")
                    games.clear()
                    games.addAll(allGames)
                    adapter.notifyDataSetChanged()
                } else {
                    buscarJuegosPorNombre(searchText)
                }
            }
        })

    }

    private fun cargarJuegosDesdeAPI() {
        val url =
            "https://api.rawg.io/api/games?key=8f1d2939357d42f7af531dc43d0e2172&page_size=50" // 50 juegos
        val gson = Gson()
        val request = JsonObjectRequest(url, { response ->

            val gameData = gson.fromJson(response.toString(), Game::class.java)
            games.clear()
            gameData.results?.let {
                games.addAll(it)
                allGames.clear()
                allGames.addAll(it)
                Log.d("DEBUG", "Juegos originales cargados: ${allGames.size}")
            }
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

    private fun buscarJuegosPorNombre(nombre: String) {
        val url =
            "https://api.rawg.io/api/games?key=8f1d2939357d42f7af531dc43d0e2172&search=${nombre}"

        val gson = Gson()
        val request = JsonObjectRequest(url, { response ->
            val currentQuery = binding.searchEditText.text.toString().trim()
            if (currentQuery != nombre) {
                // Ya no corresponde a esta búsqueda, así que la ignoramos.
                Log.d("DEBUG", "Ignorando resultados de búsqueda \"$nombre\" porque el input es \"$currentQuery\"")
                return@JsonObjectRequest
            }
            val gameData = gson.fromJson(response.toString(), Game::class.java)
            games.clear()
            gameData.results?.let { games.addAll(it) }
            adapter.notifyDataSetChanged()
        }, {
            Snackbar.make(binding.root, "Error al buscar juegos", Snackbar.LENGTH_SHORT).show()
            it.printStackTrace()
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }


}