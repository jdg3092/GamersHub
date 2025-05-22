package com.example.gamershub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamershub.R
import com.example.gamershub.model.Tema

class ForumAdapter(val temas: MutableList<Tema>, val context: Context) :
    RecyclerView.Adapter<ForumAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textTema)

    }
    override fun getItemCount(): Int {
        return temas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumAdapter.MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tema, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: ForumAdapter.MyHolder, position: Int) {
        val tema = temas[position]
        holder.title.text = tema.nombre

    }



}