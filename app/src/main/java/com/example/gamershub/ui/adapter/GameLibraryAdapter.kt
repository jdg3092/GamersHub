package com.example.gamershub.ui.adapter

import android.content.Context
import android.util.Log
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


class GameLibraryAdapter(
    val games: List<GameResult>,
    val context: Context,
    val onItemLongClick: (GameResult) -> Unit
) :
    RecyclerView.Adapter<GameLibraryAdapter.MyHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val toolbarGameItem: Toolbar = itemView.findViewById(R.id.toolbarGameItem)
        val textReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)

        init {
            itemView.setOnLongClickListener {
                onItemLongClick(games[adapterPosition]) // Llamamos al callback cuando se hace una pulsación larga
                true // Indicamos que hemos manejado el evento
            }
            toolbarGameItem.inflateMenu(R.menu.menu_game_item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val game = games[position]
        Glide.with(context)
            .load(game.backgroundImage)
            .placeholder(R.drawable.ic_placeholder_game)
            .error(R.drawable.ic_placeholder_game)
            .into(holder.imgGame)
        Log.d("DEBUG", "Imagen: ${game.backgroundImage}")
        holder.toolbarGameItem.title = game.name
        holder.textReleaseDate.text = "Lanzamiento: ${game.released ?: "Fecha no disponible"}"

        holder.toolbarGameItem.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addTracker -> {
                    auth = FirebaseAuth.getInstance()
                    database =
                        FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
                    val currentUser = auth.currentUser
                    val reference = game.id?.let { it ->
                        database.reference.child("users").child(currentUser!!.uid)
                            .child("MyGameTracker").child(it.toString())
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
}
