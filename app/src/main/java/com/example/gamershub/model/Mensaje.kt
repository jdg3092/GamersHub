package com.example.gamershub.model

import com.google.firebase.database.Exclude

data class Mensaje(
    val autor: String = "",
    val texto: String = "",
    val timestamp: Long = 0,
    @get:Exclude val key: String = ""
)
