package com.example.gamershub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamershub.R
import com.example.gamershub.model.GameResult

class GameReleaseAdapter(
    private val games: List<GameResult>,
    private val context: Context,
    private val onLongClick: (GameResult) -> Unit
) : RecyclerView.Adapter<GameReleaseAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val tvGameName: TextView = itemView.findViewById(R.id.textTitle)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)
        val tvPlatforms: TextView = itemView.findViewById(R.id.textPlatforms)
        val tvGenres: TextView = itemView.findViewById(R.id.textGenres)
        val tvRating: TextView = itemView.findViewById(R.id.textRating)

        init {
            itemView.setOnLongClickListener {
                val game = games[adapterPosition]
                onLongClick(game)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game_release, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]


        Glide.with(context).load(game.backgroundImage).into(holder.imgGame)

        holder.tvGameName.text = game.name ?: "Sin nombre"
        holder.tvReleaseDate.text = "Lanzamiento: ${game.released ?: "N/D"}"

        val plataformas = game.platforms
            ?.mapNotNull { it.platform?.name }
            ?.joinToString(", ") ?: "N/D"
        holder.tvPlatforms.text = "Plataformas: $plataformas"

        val generos = game.genres?.mapNotNull { it.name }?.joinToString(", ") ?: "N/D"
        holder.tvGenres.text = "Géneros: $generos"

        holder.tvRating.text = "Rating: ${game.rating ?: 0.0}"
    }

    override fun getItemCount(): Int = games.size
}
