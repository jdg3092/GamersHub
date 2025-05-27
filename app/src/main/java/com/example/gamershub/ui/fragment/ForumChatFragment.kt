package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentForumchatBinding
import com.example.gamershub.model.Mensaje
import com.example.gamershub.ui.adapter.ForumChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForumChatFragment : Fragment() {
    private lateinit var binding: FragmentForumchatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ForumChatAdapter
    private var mensajes: MutableList<Mensaje> = mutableListOf()
    private var temaId: String? = null
    private var temaNombre: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        temaId = arguments?.getString("temaId")
        temaNombre = arguments?.getString("temaNombre")
        auth = FirebaseAuth.getInstance()
        database =
            FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
        adapter = ForumChatAdapter(mensajes, requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumchatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.toolbarForumChat.inflateMenu(R.menu.menu_forumchat)
        binding.toolbarForumChat.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.toolbarForumChat.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.volverForumFragment -> {
                    findNavController().navigate(R.id.action_forumChatFragment_to_forumFragment)
                    return@setOnMenuItemClickListener true
                }

            }
            return@setOnMenuItemClickListener true
        }
        binding.toolbarForumChat.title = temaNombre
        binding.recyclerMensajes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMensajes.adapter = adapter

        cargarMensajes()

        binding.btnEnviar.setOnClickListener {
            val texto = binding.editMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                enviarMensaje(texto)
                binding.editMensaje.setText("")
            }
        }
    }

    private fun cargarMensajes() {
        val ref = database.reference.child("temas").child(temaId!!).child("mensajes")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mensajes.clear()
                for (mensajeSnap in snapshot.children) {
                    val mensaje = mensajeSnap.getValue(Mensaje::class.java)
                    if (mensaje != null) mensajes.add(mensaje)
                }
                adapter.notifyDataSetChanged()
                binding.recyclerMensajes.scrollToPosition(mensajes.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun enviarMensaje(texto: String) {
        val mensaje = Mensaje(
            autor = auth.currentUser?.email ?: "anonimo",
            texto = texto,
            timestamp = System.currentTimeMillis()
        )
        database.reference.child("temas").child(temaId!!).child("mensajes").push().setValue(mensaje)
    }


}