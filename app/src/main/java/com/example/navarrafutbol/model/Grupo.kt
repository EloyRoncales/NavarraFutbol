package com.example.navarrafutbol.model

import com.example.navarrafutbol.wrapper.PartidoWrapper

data class Grupo(
    val id: Int,
    val categoriaId: Int,
    val grupo: String,
    val partidosWrapper: PartidoWrapper
)
