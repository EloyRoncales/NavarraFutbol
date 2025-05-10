package com.example.navarrafutbol.model

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
