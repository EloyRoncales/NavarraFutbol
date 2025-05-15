package com.example.navarrafutbol.retrofit

import com.example.navarrafutbol.service.NavarraFutbolApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que configura y proporciona una instancia de Retrofit para acceder a la API de fútbol navarro.
 *
 * Utiliza `GsonConverterFactory` para la conversión automática de JSON a objetos Kotlin.
 */
object RetrofitClient {

    /**
     * Instancia preconfigurada de Retrofit con la URL base de la API.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://navarrafutbolapi.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Instancia de la interfaz [NavarraFutbolApi] generada por Retrofit.
     */
    val api: NavarraFutbolApi = retrofit.create(NavarraFutbolApi::class.java)

    /**
     * Devuelve la instancia de Retrofit directamente, si se desea trabajar con otras interfaces.
     *
     * @return Instancia global de [Retrofit].
     */
    fun getInstance(): Retrofit = retrofit
}
