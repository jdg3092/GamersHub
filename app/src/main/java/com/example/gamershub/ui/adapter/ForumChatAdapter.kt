package com.example.gamershub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamershub.R
import com.example.gamershub.model.Mensaje

class ForumChatAdapter(val mensajes: MutableList<Mensaje>, val context: Context) : RecyclerView.Adapter<ForumChatAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto: TextView = itemView.findViewById(R.id.textMensaje)
        val autor: TextView = itemView.findViewById(R.id.textAutor)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mensaje, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.texto.text = mensaje.texto
        holder.autor.text = mensaje.autor
    }
}