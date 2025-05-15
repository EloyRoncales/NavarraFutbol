package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.*
import com.example.navarrafutbol.response.*
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz que define las operaciones de red para acceder a datos del fútbol navarro.
 *
 * Utiliza Retrofit para consumir una API REST.
 */
interface NavarraFutbolApi {

    /**
     * Obtiene los partidos correspondientes a una categoría específica.
     *
     * @param id ID de la categoría.
     * @return Un objeto [Categoria] que contiene los grupos y partidos asociados.
     */
    @GET("api/partidos/categoria/{categoriaId}/partidos")
    suspend fun getPartidos(@Path("categoriaId") id: Int): Categoria

    /**
     * Obtiene la clasificación de equipos dentro de una categoría.
     *
     * @param id ID de la categoría.
     * @return Lista de objetos [CategoriaClasificacionResponse] con posiciones y estadísticas.
     */
    @GET("api/clasificaciones/{categoriaId}/clasificacion")
    suspend fun getClasificacionPorCategoria(
        @Path("categoriaId") id: Int
    ): List<CategoriaClasificacionResponse>

    /**
     * Obtiene los jugadores de un equipo específico.
     *
     * @param equipoId ID del equipo.
     * @return Lista de objetos [Jugador] asociados al equipo.
     */
    @GET("api/Jugadores/equipo/{equipoId}")
    suspend fun getJugadoresPorEquipo(
        @Path("equipoId") equipoId: Int
    ): List<Jugador>

    /**
     * Obtiene la información detallada de un equipo a partir de su ID.
     *
     * @param id ID del equipo.
     * @return Un objeto [EquipoFav] si existe, o `null`.
     */
    @GET("api/equipos/{id}")
    suspend fun getEquipo(@Path("id") id: Int): EquipoFav?
}
