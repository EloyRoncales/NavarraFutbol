package com.example.navarrafutbol.model

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

