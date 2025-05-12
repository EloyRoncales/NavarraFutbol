package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.Noticia
import retrofit2.http.GET

interface NoticiaApi {
    @GET("noticias")
    suspend fun getNoticias(): List<Noticia>
}