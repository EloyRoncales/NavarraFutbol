package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.Categoria
import com.example.navarrafutbol.response.CategoriaResponse
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
}