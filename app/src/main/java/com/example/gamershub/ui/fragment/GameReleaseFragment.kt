package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentGamereleaseBinding
import com.example.gamershub.model.Game
import com.example.gamershub.model.GameResponse
import com.example.gamershub.model.GameResult
import com.example.gamershub.ui.adapter.GameReleaseAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class GameReleaseFragment : Fragment() {

    private lateinit var binding: FragmentGamereleaseBinding
    private lateinit var adapter: GameReleaseAdapter
    private var games: MutableList<GameResult> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = GameReleaseAdapter(games, requireContext()) { game ->
            navegarADetalleJuego(game)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamereleaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()



        binding.recyclerViewJuegosRelease.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewJuegosRelease.adapter = adapter

        binding.btnBuscar.setOnClickListener {
            val startDate = binding.etFechaInicio.text.toString().trim()
            val endDate = binding.etFechaFin.text.toString().trim()
            if (startDate.isNotBlank() && endDate.isNotBlank()) {
                buscarJuegosPorFecha(startDate, endDate)
            } else {
                Snackbar.make(binding.root, "Introduce ambas fechas", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarJuegosPorFecha(start: String, end: String) {
        val url = "https://api.rawg.io/api/games?key=8f1d2939357d42f7af531dc43d0e2172&dates=$start,$end&ordering=-released&page_size=100"
        val gson = Gson()
        val request = JsonObjectRequest(url, { response ->
            Log.d("GameReleaseFragment", "JSON Response: $response")
            try {
                val gameData = gson.fromJson(response.toString(), GameResponse::class.java)
                games.clear()
                gameData.results?.let {
                    Log.d("GameReleaseFragment", "Juegos recibidos: ${it.size}")
                    games.addAll(it)
                }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("GameReleaseFragment", "Error al parsear JSON", e)
                Snackbar.make(binding.root, "Error al procesar los datos", Snackbar.LENGTH_SHORT).show()
            }
        }, { error ->
            Log.e("GameReleaseFragment", "Error al cargar los juegos", error)
            Snackbar.make(binding.root, "Error al cargar los juegos", Snackbar.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }


    private fun navegarADetalleJuego(game: GameResult) {
        val bundle = Bundle()
        game.id?.let { bundle.putLong("game_id", it) }
        findNavController().navigate(R.id.action_gameReleaseFragment_to_detailsGameFragment, bundle)
    }
}
