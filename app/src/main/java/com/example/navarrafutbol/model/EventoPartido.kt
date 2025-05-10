package com.example.navarrafutbol.model

data class EventoPartido(
    val id: Int,
    val partidoId: Int,
    val jugadorId: Int,
    val partido: Partido,
    val jugador: Jugador,
    val tipo: String,
    val minuto: Int
)

