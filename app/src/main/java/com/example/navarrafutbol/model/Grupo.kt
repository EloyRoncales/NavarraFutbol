package com.example.navarrafutbol.model

import com.example.navarrafutbol.wrapper.ClasificacionesWrapper
import com.example.navarrafutbol.wrapper.EquiposWrapper
import com.example.navarrafutbol.wrapper.JugadoresWrapper
import com.example.navarrafutbol.wrapper.PartidoWrapper
import com.google.gson.annotations.SerializedName

data class Grupo(
    val id: Int,
    val nombre: String,
    val categoriaId: Int,
    val categoria: Categoria,
    @SerializedName("equipos") val equiposWrapper: EquiposWrapper,
    @SerializedName("jugadores") val jugadoresWrapper: JugadoresWrapper,
    @SerializedName("clasificaciones") val clasificacionesWrapper: ClasificacionesWrapper,
    @SerializedName("partidos") val partidosWrapper: PartidoWrapper
) {
    // Propiedades para obtener las listas directamente
    val equipos: List<Equipo>
        get() = equiposWrapper.equipos

    val jugadores: List<Jugador>
        get() = jugadoresWrapper.jugadores

    val clasificaciones: List<Clasificacion>
        get() = clasificacionesWrapper.clasificaciones

    val partidos: List<Partido>
        get() = partidosWrapper.partidos
}
