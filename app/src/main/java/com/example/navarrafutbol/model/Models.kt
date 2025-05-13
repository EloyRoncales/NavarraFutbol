package com.example.navarrafutbol.model

import com.google.gson.annotations.SerializedName


data class Categoria(
    val Categoria: String,
    val Grupos: List<Grupo>
)

data class Clasificacion(
    @SerializedName("Id") val id: Int,
    @SerializedName("EquipoId") val equipoId: Int,
    @SerializedName("GrupoId") val grupoId: Int,
    @SerializedName("Equipo") val equipo: Equipo? = null,
    @SerializedName("Grupo") val grupo: Grupo? = null,
    @SerializedName("Puntos") val puntos: Int,
    @SerializedName("PartidosJugados") val partidosJugados: Int,
    @SerializedName("PartidosGanados") val partidosGanados: Int,
    @SerializedName("PartidosEmpatados") val partidosEmpatados: Int,
    @SerializedName("PartidosPerdidos") val partidosPerdidos: Int,
    @SerializedName("GolesFavor") val golesFavor: Int,
    @SerializedName("GolesContra") val golesContra: Int
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
    val id: Int,
    val Grupo: String,
    val Partidos: List<Partido>,
    val Categoria: Categoria
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