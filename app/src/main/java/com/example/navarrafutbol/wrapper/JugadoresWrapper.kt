package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Jugador
import com.google.gson.annotations.SerializedName

data class JugadoresWrapper(
    @SerializedName("\$values")
    val jugadores: List<Jugador>
)