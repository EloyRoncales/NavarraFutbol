package com.example.navarrafutbol.service

import com.example.navarrafutbol.model.Noticia
import retrofit2.http.GET

/**
 * Interfaz para acceder a las noticias deportivas desde la API externa.
 *
 * Utiliza Retrofit para realizar solicitudes HTTP.
 */
interface NoticiaApi {

    /**
     * Obtiene una lista de noticias deportivas.
     *
     * @return Lista de objetos [Noticia] recuperados desde el endpoint `noticias`.
     */
    @GET("noticias")
    suspend fun getNoticias(): List<Noticia>
}
