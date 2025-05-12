package com.example.navarrafutbol.wrapper

import com.example.navarrafutbol.model.*
import com.google.gson.annotations.SerializedName

data class GrupoWrapper(
    val grupos: List<Grupo>
)

data class PartidoWrapper(
    val partidos: List<Partido>
)

data class ClasificacionWrapper(
    @SerializedName("\$values")
    val clasificaciones: List<Clasificacion>
)

data class EquipoWrapper(
    @SerializedName("\$values")
    val equipos: List<Equipo>
)

data class JugadorWrapper(
    @SerializedName("\$values")
    val jugadores: List<Jugador>
)

data class EventoPartidoWrapper(
    @SerializedName("\$values")
    val eventos: List<EventoPartido>
)

data class NoticiaWrapper(
    @SerializedName("\$values")
    val noticias: List<Noticia>
)
