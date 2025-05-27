package com.example.gamershub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamershub.R
import com.example.gamershub.model.GameResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GameReleaseAdapter(
    private val games: List<GameResult>,
    private val context: Context,
    private val onLongClick: (GameResult) -> Unit
) : RecyclerView.Adapter<GameReleaseAdapter.GameViewHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val tvGameName: Toolbar = itemView.findViewById(R.id.toolbarGameReleaseItem)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)


        init {
            itemView.setOnLongClickListener {
                val game = games[adapterPosition]
                onLongClick(game)
                true
            }
            tvGameName.inflateMenu(R.menu.menu_game_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game_release, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]


        Glide.with(context).load(game.backgroundImage).into(holder.imgGame)

        holder.tvGameName.title = game.name ?: "Sin nombre"
        holder.tvReleaseDate.text = "Lanzamiento: ${game.released ?: "N/D"}"
        holder.tvGameName.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addTracker -> {
                    auth = FirebaseAuth.getInstance()
                    database =
                        FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
                    val currentUser = auth.currentUser
                    val reference = game.id?.let { id -> database.reference
                        .child("users")
                        .child(currentUser!!.uid)
                        .child("GameTracker")
                        .child("QuieroJugar") // ← Aquí especificas la categoría
                        .child(id.toString())
                    }
                    reference?.setValue(game)?.addOnSuccessListener {
                        Snackbar.make(holder.itemView, "Añadido a My Game Tracker", Snackbar.LENGTH_SHORT).show()

                    }?.addOnFailureListener {
                        Snackbar.make(holder.itemView, "Error al guardar en My Game Tracker", Snackbar.LENGTH_SHORT).show()

                    }

                    return@setOnMenuItemClickListener true
                }

            }
            return@setOnMenuItemClickListener true
        }

    }

    override fun getItemCount(): Int = games.size
}
