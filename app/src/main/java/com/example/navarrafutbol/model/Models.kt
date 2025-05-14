package com.example.navarrafutbol.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categoria(
    val Categoria: String,
    val Grupos: List<Grupo>
) : Serializable

data class Clasificacion(
    @SerializedName("Id") val id: Int,
    @SerializedName("EquipoId") val equipoId: Int,
    @SerializedName("GrupoId") val grupoId: Int,
    @SerializedName("Equipo") val equipo: Equipo?,
    @SerializedName("Grupo") val grupo: Grupo?,
    @SerializedName("Puntos") val puntos: Int,
    @SerializedName("PartidosJugados") val partidosJugados: Int,
    @SerializedName("PartidosGanados") val partidosGanados: Int,
    @SerializedName("PartidosEmpatados") val partidosEmpatados: Int,
    @SerializedName("PartidosPerdidos") val partidosPerdidos: Int,
    @SerializedName("GolesFavor") val golesFavor: Int,
    @SerializedName("GolesContra") val golesContra: Int
) : Serializable

data class Equipo(
    val id: Int,
    val nombre: String,
    val escudoUrl: String,
    val estadio: String
) : Serializable

data class EquipoFav(
    @SerializedName("Id") val id: Int,
    @SerializedName("Nombre") val nombre: String,
    @SerializedName("EscudoUrl") val escudoUrl: String,
    @SerializedName("Estadio") val estadio: String
)

data class EventoPartido(
    val id: Int,
    val partidoId: Int,
    val jugadorId: Int,
    val partido: Partido,
    val jugador: Jugador,
    val tipo: String,
    val minuto: Int
) : Serializable

data class Grupo(
    val id: Int,
    val Grupo: String,
    val Partidos: List<Partido>,
    val Categoria: Categoria,
    val Clasificaciones: List<Clasificacion>? = null
) : Serializable

data class Jugador(
    @SerializedName("Id") val id: Int,
    @SerializedName("Nombre") val nombre: String,
    @SerializedName("Posicion") val posicion: String,
    @SerializedName("Edad") val edad: Int,
    @SerializedName("Goles") val goles: Int
) : Serializable

data class Partido(
    val Id: Int,
    val Fecha: String,
    val EquipoLocal: String,
    val EquipoVisitante: String,
    val GolesLocal: Int?,
    val GolesVisitante: Int?,
    val EscudoLocalUrl: String?,
    val EscudoVisitanteUrl: String?
) : Serializable

data class GrupoConClasificacion(
    val id: Int,
    val grupo: String,
    val clasificaciones: List<Clasificacion>
) : Serializable

data class ClasificacionItem(
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
    val equipo: Equipo
) : Serializable
