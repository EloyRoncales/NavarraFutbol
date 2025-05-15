package com.example.navarrafutbol.response

import com.example.navarrafutbol.model.ClasificacionItem

/**
 * Representa una categoría con su lista de grupos.
 */
data class CategoriaResponse(
    val id: Int?,
    val categoria: String?,
    val grupos: List<GrupoResponse>?
)

/**
 * Contiene un grupo de una categoría y su lista de clasificaciones.
 */
data class CategoriaClasificacionResponse(
    val id: Int,
    val grupo: String,
    val clasificaciones: List<ClasificacionItem>
)

/**
 * Agrupación de clasificaciones asociadas a un grupo.
 */
data class GrupoConClasificacionResponse(
    val id: Int,
    val grupo: String,
    val clasificaciones: List<ClasificacionResponse>
)

/**
 * Representa un grupo dentro de una categoría, con sus partidos y tabla de clasificación.
 */
data class GrupoResponse(
    val id: Int,
    val grupo: String?,
    val partidos: List<PartidoResponse>?,
    val clasificaciones: List<ClasificacionItem>
)

/**
 * Representa un partido jugado entre dos equipos, incluyendo los goles.
 */
data class PartidoResponse(
    val id: Int?,
    val fecha: String?,
    val equipoLocal: String?,
    val equipoVisitante: String?,
    val golesLocal: Int?,
    val golesVisitante: Int?
)

/**
 * Información estadística de un equipo en la clasificación.
 */
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

/**
 * Información básica de un equipo (nombre, escudo y estadio).
 */
data class EquipoResponse(
    val id: Int?,
    val nombre: String?,
    val escudoUrl: String?,
    val estadio: String?
)
