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
import com.example.gamershub.model.Game


class GameLibraryAdapter(val games: List<Game>, val context: Context): RecyclerView.Adapter<GameLibraryAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgGame: ImageView = itemView.findViewById(R.id.imgGame)
        val textGameName: TextView = itemView.findViewById(R.id.textGameName)
        val textReleaseDate: TextView = itemView.findViewById(R.id.textReleaseDate)
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
        Glide.with(context).load(game.results?.get(position)?.backgroundImage).into(holder.imgGame)
        holder.textGameName.text = game.results?.get(position)?.name
        holder.textReleaseDate.text = game.results?.get(position)?.released

    }

}