package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentProfileinfoaccountBinding
import com.example.gamershub.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileInfoAccountFragment : Fragment() {
    private lateinit var binding: FragmentProfileinfoaccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database =
            FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileinfoaccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.toolbarPerfilInfo.inflateMenu(R.menu.menu_perfil)
        binding.toolbarPerfilInfo.overflowIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        binding.toolbarPerfilInfo.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.volverMainFragmnet ->{
                    findNavController().navigate(R.id.action_profileInfoAccountFragment_to_mainFragment)
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true
        }

        val currentuser = auth.currentUser?.uid
        if (currentuser != null) {
            val reference = database.reference.child("users").child(currentuser)
            // Cargar datos desde la BBDD
            reference.get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    binding.editNombre.setText(user.nombre)
                    binding.editApellido.setText(user.apellido)
                    binding.editTelefono.setText(user.telefono)
                    binding.editEmail.setText(user.correo)
                    binding.editEmail.isEnabled = false // No editable
                    binding.editDireccion.setText(user.direccion)
                }
            }

            // Guardar cambios en la BBDD
            binding.btnModificar.setOnClickListener {
                val updatedUser = User(
                    nombre = binding.editNombre.text.toString(),
                    apellido = binding.editApellido.text.toString(),
                    correo = binding.editEmail.text.toString(),
                    telefono = binding.editTelefono.text.toString(),
                    direccion = binding.editDireccion.text.toString()
                )
                reference.setValue(updatedUser).addOnCompleteListener {
                    Snackbar.make(binding.root, "Cambios guardados", Snackbar.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.root,
                        "Error al guardar los cambios",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

}