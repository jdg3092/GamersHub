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

class GameTrackerAdapter(
    val games: MutableList<GameResult>,
    val context: Context,
    val estadoActual: String, // "QuieroJugar", "Jugando", "Terminado"
    val onItemLongClick: (GameResult) -> Unit
) : RecyclerView.Adapter<GameTrackerAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val toolbarGameItem: Toolbar = itemView.findViewById(R.id.toolbarGameItem)
        val textReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)
        init {
            itemView.setOnLongClickListener {
                onItemLongClick(games[adapterPosition]) // Llamamos al callback cuando se hace una pulsación larga
                true // Indicamos que hemos manejado el evento
            }
            toolbarGameItem.inflateMenu(R.menu.menu_tracker_item)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

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
                R.id.moverAJugando -> {
                    moverJuegoAFirebase(game, "Jugando")
                    Snackbar.make(
                        holder.itemView,
                        "Añadido a Jugando",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.moverATerminado -> {
                    moverJuegoAFirebase(game, "Terminado")
                    Snackbar.make(
                        holder.itemView,
                        "Añadido a Terminado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
    }
    private fun moverJuegoAFirebase(juego: GameResult, destino: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val dbRef = FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/").reference

        val origen = dbRef.child("users").child(uid).child("GameTracker").child(estadoActual).child(juego.id.toString())
        val destinoRef = dbRef.child("users").child(uid).child("GameTracker").child(destino).child(juego.id.toString())

        destinoRef.setValue(juego).addOnSuccessListener {
            origen.removeValue().addOnSuccessListener {
                val index = games.indexOfFirst { it.id == juego.id }
                if (index != -1) {
                    games.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }
    }
}