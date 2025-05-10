package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Clasificacion
import com.google.gson.annotations.SerializedName

data class ClasificacionesWrapper(
    @SerializedName("\$values")
    val clasificaciones: List<Clasificacion>
)