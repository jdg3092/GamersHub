package com.example.gamershub.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamershub.R

import com.example.gamershub.model.GameResult


class GameLibraryAdapter(private val games: List<GameResult>, private val context: Context) :
    RecyclerView.Adapter<GameLibraryAdapter.MyHolder>() {

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val textGameName: TextView = itemView.findViewById(R.id.textGameName)
        val textReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)
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
        holder.textGameName.text = game.name
        holder.textReleaseDate.text = game.released
    }
}
