package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Grupo
import com.google.gson.annotations.SerializedName

data class GrupoWrapper(
    @SerializedName("\$values")
    val grupos: List<Grupo>
)