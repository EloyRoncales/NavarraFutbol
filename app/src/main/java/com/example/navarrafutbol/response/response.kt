package com.example.navarrafutbol.response

data class CategoriaResponse(
    val id: Int?,
    val categoria: String?,
    val grupos: List<GrupoResponse>?
)

data class GrupoResponse(
    val grupo: String?,
    val partidos: List<PartidoResponse>?
)

data class PartidoResponse(
    val id: Int?,
    val fecha: String?,
    val equipoLocal: String?,
    val equipoVisitante: String?,
    val golesLocal: Int?,
    val golesVisitante: Int?
)
