package com.example.navarrafutbol.model

import com.google.gson.JsonElement
import java.util.*

data class Partido(
    val id: Int,
    val grupoId: Int,
    val localId: Int,
    val visitanteId: Int,
    val grupo: Grupo,
    val local: Equipo,
    val visitante: Equipo,
    val golesLocal: Int,
    val golesVisitante: Int,
    val fecha: String,
    val eventosPartido: JsonElement
)


