package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Equipo
import com.google.gson.annotations.SerializedName

data class EquiposWrapper(
    @SerializedName("\$values")
    val equipos: List<Equipo>
)