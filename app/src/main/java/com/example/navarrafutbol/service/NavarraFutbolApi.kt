package com.example.navarrafutbol.service

import com.example.navarrafutbol.response.CategoriaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface NavarraFutbolApi {

    @GET("api/Partidos/categoria/{categoriaId}/partidos")
    fun getPartidosByCategoria(@Path("categoriaId") categoriaId: Int): Call<CategoriaResponse>

}