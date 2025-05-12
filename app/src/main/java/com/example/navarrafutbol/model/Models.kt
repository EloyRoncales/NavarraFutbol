package com.example.navarrafutbol.model


data class Categoria(
    val Categoria: String,
    val Grupos: List<Grupo>
)

data class Clasificacion(
    val id: Int,
    val equipoId: Int,
    val grupoId: Int,
    val equipo: Equipo,
    val grupo: Grupo,
    val puntos: Int,
    val partidosJugados: Int,
    val partidosGanados: Int,
    val partidosEmpatados: Int,
    val partidosPerdidos: Int,
    val golesFavor: Int,
    val golesContra: Int
)

data class Equipo(
    val id: Int,
    val nombre: String,
    val escudoUrl: String,
    val estadio: String
)

data class EventoPartido(
    val id: Int,
    val partidoId: Int,
    val jugadorId: Int,
    val partido: Partido,
    val jugador: Jugador,
    val tipo: String,
    val minuto: Int
)

data class Grupo(
    val Grupo: String,
    val Partidos: List<Partido>
)

data class Jugador(
    val id: Int,
    val nombre: String,
    val edad: Int,
    val posicion: String,
    val equipoId: Int,
    val grupoId: Int,
    val equipo: Equipo,
    val grupo: Grupo,
    val goles: Int = 0,
    val tarjetasAmarillas: Int,
    val tarjetasRojas: Int,
    val eventosPartido: List<EventoPartido>
)

data class Partido(
    val Id: Int,
    val Fecha: String,
    val EquipoLocal: String,
    val EquipoVisitante: String,
    val GolesLocal: Int?,
    val GolesVisitante: Int?
)