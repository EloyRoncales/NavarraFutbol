package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Partido
import com.google.gson.annotations.SerializedName

data class PartidoWrapper(
    @SerializedName("\$values")
    val partidos: List<Partido>
)