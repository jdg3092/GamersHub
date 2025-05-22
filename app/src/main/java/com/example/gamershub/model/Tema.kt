package com.example.gamershub.model

data class Tema(
    val id: String = "",    // clave del tema (Firebase push key)
    val nombre: String = "",
    val creador: String = "",
    val timestamp: Long = 0L
)
