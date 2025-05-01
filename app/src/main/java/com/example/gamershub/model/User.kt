package com.example.gamershub.model

import java.io.Serializable

class User(
    var nombre: String? = null,
    var apellido: String? = null,
    var correo: String? = null,
    var password: String? = null,
    var telefono: String? = null,
    var direccion: String? = null
): Serializable {
}