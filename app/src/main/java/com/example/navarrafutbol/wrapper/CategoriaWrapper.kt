package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.Categoria
import com.google.gson.annotations.SerializedName

data class CategoriaWrapper(
    @SerializedName("\$values")
    val categorias: List<Categoria>
)

