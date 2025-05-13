package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.*
import com.example.navarrafutbol.model.*
import com.example.navarrafutbol.response.CategoriaClasificacionResponse
import com.example.navarrafutbol.response.CategoriaResponse
import com.example.navarrafutbol.response.ClasificacionResponse
import com.example.navarrafutbol.response.GrupoResponse
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





}