package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.gamershub.databinding.FragmentDetailsgameBinding
import com.example.gamershub.model.GameResult
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class DetailsGameFragment : Fragment() {
    private lateinit var binding: FragmentDetailsgameBinding
    private var gameId: Long? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Recupera el ID del juego pasado en el Bundle
        arguments?.let {
            gameId = it.getLong("game_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsgameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // El gameId para hacer la solicitud a la API
        gameId?.let {
            cargarDetallesDelJuego(it)
        }
    }

    private fun cargarDetallesDelJuego(gameId: Long) {
        val url = "https://api.rawg.io/api/games/$gameId?key=8f1d2939357d42f7af531dc43d0e2172"
        val gson = Gson()

        val request = JsonObjectRequest(url, { response ->
            val gameDetail = gson.fromJson(response.toString(), GameResult::class.java)
            actualizarUIConDetalles(gameDetail)
        }, {
            // Manejo de errores
            Snackbar.make(requireView(), "Error al cargar los detalles", Snackbar.LENGTH_SHORT)
                .show()
            it.printStackTrace()
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    // Función para actualizar la UI con los detalles del juego
    private fun actualizarUIConDetalles(game: GameResult) {
        val nombresPlataformas = game.platforms?.mapNotNull { it.platform?.name } ?: emptyList()
        val nombresGeneros = game.genres?.mapNotNull { it.name } ?: emptyList()
        val nombresTiendas = game.stores?.mapNotNull { it.store?.name } ?: emptyList()
        val nombresTags = game.tags?.mapNotNull { it.name } ?: emptyList()

        binding.textViewTitle.text = game.name
        binding.textViewRelease.text = "Lanzamiento: ${game.released}"
        binding.textViewRating.text = "Rating: ${game.rating}"
        binding.textViewPlaytime.text = "Playtime: ${game.playtime}h"
        binding.textViewPlatforms.text = "Plataformas: ${nombresPlataformas.joinToString(", ")}"
        binding.textViewGenres.text = "Géneros: ${nombresGeneros.joinToString(", ")}"
        binding.textViewStores.text = "Tiendas: ${nombresTiendas.joinToString(", ")}"
        binding.textViewTags.text = "Etiquetas: ${nombresTags.joinToString(", ")}"


        // Cargar la imagen de fondo del juego
        Glide.with(requireContext())
            .load(game.backgroundImage)
            .into(binding.imageView)
    }

}