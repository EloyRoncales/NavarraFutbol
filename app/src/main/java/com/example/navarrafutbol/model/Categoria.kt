package com.example.navarrafutbol.model

import com.example.navarrafutbol.wrapper.GrupoWrapper

data class Categoria(
    val id: Int,
    val categoria: String,
    val gruposWrapper: GrupoWrapper
)
