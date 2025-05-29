package com.example.gamershub.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gamershub.MainActivity
import com.example.gamershub.R
import com.example.gamershub.model.Mensaje
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ForumChatAdapter(
    val mensajes: MutableList<Mensaje>,
    val context: Context,
    private val auth: FirebaseAuth, // PASA EL AUTH DESDE EL FRAGMENT
    private val temaId: String
) : RecyclerView.Adapter<ForumChatAdapter.MyHolder>() {
    private lateinit var database: FirebaseDatabase

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto: TextView = itemView.findViewById(R.id.textMensaje)
        val autor: Toolbar = itemView.findViewById(R.id.itemToolbar)

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
        holder.autor.title = mensaje.autor

        holder.autor.menu.clear()

        // Solo muestra el menú si el autor del mensaje es el usuario actual
        if (mensaje.autor == auth.currentUser?.email) {
            holder.autor.inflateMenu(R.menu.menu_mensaje)
            holder.autor.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_eliminar -> {
                        eliminarMensaje(position)
                        Snackbar.make(holder.itemView, "Eliminado mensaje", Snackbar.LENGTH_SHORT)
                            .show()
                        return@setOnMenuItemClickListener true
                    }

                    R.id.menu_editar -> {
                        val bundle = Bundle().apply {
                            putString("mensajeTexto", mensaje.texto)
                            putString("mensajeKey", mensaje.key)
                            putString("temaId", temaId)
                        }
                        (context as MainActivity).findNavController(R.id.nav_host_fragment_content_main)
                            .navigate(R.id.action_forumChatFragment_to_editarMensajeDialogo, bundle)
                        return@setOnMenuItemClickListener true
                    }

                    else -> return@setOnMenuItemClickListener false
                }
            }
        } else {
            // Si no es del usuario, quita cualquier menú anterior para evitar errores de reciclado
            holder.autor.menu.clear()
        }
    }

    fun eliminarMensaje(position: Int) {
        val mensaje = mensajes[position]
        val mensajeKey = mensaje.key

        if (mensajeKey.isNotEmpty()) {
            val ref =
                FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
                    .reference
                    .child("temas")
                    .child(temaId)
                    .child("mensajes")
                    .child(mensajeKey)

            ref.removeValue().addOnSuccessListener {

            }.addOnFailureListener {

            }
        }
    }

}