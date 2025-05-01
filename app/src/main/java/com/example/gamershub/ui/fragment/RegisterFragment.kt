package com.example.gamershub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gamershub.R
import com.example.gamershub.databinding.FragmentRegisterBinding
import com.example.gamershub.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.editNombre.text.toString()
            val apellido = binding.editApellido.text.toString()
            val telefono = binding.editTelefono.text.toString()
            val correo = binding.editCorreo.text.toString()
            val pass = binding.editPass.text.toString()
            val direccion = binding.editDireccion.text.toString()

            val user = User(nombre, apellido, telefono, correo, pass, direccion)

            auth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    val currentUser = auth.currentUser
                    val reference = database.reference.child("users").child(currentUser!!.uid)
                    reference.setValue(user)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }else{
                    Snackbar.make(binding.root,"Error en el registro",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
    // https://gamershub-5a2e5-default-rtdb.europe-west1.firebasedatabase.app/
