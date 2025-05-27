package com.example.gamershub.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gamershub.MainActivity
import com.example.gamershub.R
import com.example.gamershub.model.Tema

class ForumAdapter(
    val temas: MutableList<Tema>,
    val context: Context,
    val onBorrarTema: (Tema) -> Unit,
    val onEditarTema: (Tema) -> Unit
) :
    RecyclerView.Adapter<ForumAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toolbarTemas: Toolbar = itemView.findViewById(R.id.toolbarTema)

        init {
            toolbarTemas.inflateMenu(R.menu.menu_tema)

        }

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
        holder.toolbarTemas.title = tema.nombre
        holder.toolbarTemas.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_editar -> {
                    onEditarTema(tema)
                    return@setOnMenuItemClickListener true

                }

                R.id.menu_borrar -> {
                    onBorrarTema(tema)
                    return@setOnMenuItemClickListener true
                }

                R.id.ir_chat -> {
                    val bundle = Bundle().apply {
                        putString("temaId", tema.id)
                        putString("temaNombre", tema.nombre)
                    }
                    (context as MainActivity).findNavController(R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_forumFragment_to_forumChatFragment, bundle)
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true

        }

    }


}