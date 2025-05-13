package com.example.navarrafutbol.response

import com.example.navarrafutbol.model.ClasificacionItem

data class CategoriaResponse(
    val id: Int?,
    val categoria: String?,
    val grupos: List<GrupoResponse>?
)

data class CategoriaClasificacionResponse(
    val id: Int,
    val grupo: String,
    val clasificaciones: List<ClasificacionResponse>
)

data class GrupoConClasificacionResponse(
    val id: Int,
    val grupo: String,
    val clasificaciones: List<ClasificacionResponse>
)

data class GrupoResponse(
    val id: Int,
    val grupo: String?,
    val partidos: List<PartidoResponse>?,
    val clasificaciones: List<ClasificacionItem>
)

data class PartidoResponse(
    val id: Int?,
    val fecha: String?,
    val equipoLocal: String?,
    val equipoVisitante: String?,
    val golesLocal: Int?,
    val golesVisitante: Int?
)


data class ClasificacionResponse(
    val id: Int,
    val equipoId: Int,
    val grupoId: Int,
    val puntos: Int,
    val partidosJugados: Int,
    val partidosGanados: Int,
    val partidosEmpatados: Int,
    val partidosPerdidos: Int,
    val golesFavor: Int,
    val golesContra: Int,
    val equipo: EquipoResponse
)

data class EquipoResponse(
    val id: Int?,
    val nombre: String?,
    val escudoUrl: String?,
    val estadio: String?
)

