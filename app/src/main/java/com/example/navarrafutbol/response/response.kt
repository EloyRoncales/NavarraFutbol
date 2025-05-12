package com.example.navarrafutbol.response

import com.example.navarrafutbol.wrapper.*

data class CategoriaResponse(
    val categoria: String,
    val grupos: GrupoWrapper
)

data class GrupoResponse(
    val grupo: String,
    val partidos: PartidoWrapper
)

data class PartidoResponse(
    val id: Int,
    val fecha: String,
    val equipoLocal: String,
    val equipoVisitante: String,
    val golesLocal: Int?,
    val golesVisitante: Int?
)
