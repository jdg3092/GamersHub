package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentForumBinding
import com.example.gamershub.model.Tema
import com.example.gamershub.ui.adapter.ForumAdapter
import com.example.gamershub.ui.dialog.TemasDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ForumFragment : Fragment(), TemasDialog.OnTemaCreadoListener {
    private lateinit var binding: FragmentForumBinding
    private lateinit var adapter: ForumAdapter
    private val temas = mutableListOf<Tema>()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ForumAdapter(temas, requireContext())
        auth = FirebaseAuth.getInstance()
        database =
            FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerForumTemas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerForumTemas.adapter = adapter
        binding.toolbarForum.inflateMenu(R.menu.menu_forum)
        binding.toolbarForum.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.toolbarForum.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.volverMainFragmnet -> {
                    findNavController().navigate(R.id.action_forumFragment_to_mainFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.crearTema -> {
                    val dialog = TemasDialog()
                    dialog.setListener(this) // ✅ ESTA LÍNEA ES CLAVE
                    dialog.show(parentFragmentManager, "TemasDialog")
                    return@setOnMenuItemClickListener true
                }

            }
            return@setOnMenuItemClickListener true
        }
        database.reference.child("temas").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                temas.clear()
                for (temaSnapshot in snapshot.children) {
                    val tema = temaSnapshot.getValue(Tema::class.java)
                    if (tema != null) temas.add(tema)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ForumFragment", "Error al leer temas: ${error.message}")
            }
        })

    }

    override fun onTemaCreado(nombre: String) {
        // Genera una clave única para el nuevo tema dentro del nodo "temas"
        val temaId = database.reference.child("temas").push().key ?: return
        val tema = Tema(
            id = temaId,
            nombre = nombre,
            creador = auth.currentUser?.uid ?: "anonimo", // Uid del usuario que lo ha creado
            timestamp = System.currentTimeMillis()
        )

        database.reference.child("temas").child(temaId).setValue(tema)
    }

}