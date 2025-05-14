package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.*
import com.example.navarrafutbol.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface NavarraFutbolApi {

    //@GET("api/Partidos/categoria/{id}/partidos")
    //fun getPartidosByCategoria(@Path("id") categoriaId: Int): Call<CategoriaResponse>

    //@GET("api/Partidos/categoria/{id}/partidos")
    //fun getPartidosByCategoria(
    //    @Path("id") categoriaId: Int
    // ): Call<CategoriaResponse>

    @GET("api/partidos/categoria/{categoriaId}/partidos")
    suspend fun getPartidos(@Path("categoriaId") id: Int): Categoria

    @GET("api/clasificaciones")
    suspend fun getClasificaciones(): List<ClasificacionResponse>

    @GET("Api/equipos")
    fun getEquipos(): Call<List<Equipo>>

    @GET("api/Grupos")
    suspend fun getGrupos(): List<Grupo>

    @GET("api/clasificaciones/{categoriaId}/clasificacion")
    suspend fun getClasificacionPorCategoria(
        @Path("categoriaId") id: Int
    ): List<CategoriaClasificacionResponse>


    @GET("api/Clasificacion/grupos-con-clasificacion")
    suspend fun getGruposConClasificacion(): List<GrupoConClasificacion>


    @GET("api/Jugadores/equipo/{equipoId}")
    suspend fun getJugadoresPorEquipo(
        @Path("equipoId") equipoId: Int
    ): List<Jugador>

    @GET("api/equipos/{id}")
    suspend fun getEquipo(@Path("id") id: Int): EquipoFav?


}