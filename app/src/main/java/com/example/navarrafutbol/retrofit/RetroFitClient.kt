package com.example.navarrafutbol.retrofit

import com.example.navarrafutbol.service.NavarraFutbolApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://navarrafutbolapi.onrender.com/" // Reemplaza con la URL de tu API

    val instance: NavarraFutbolApi by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Tiempo para establecer la conexión
            .readTimeout(30, TimeUnit.SECONDS)    // Tiempo para leer los datos
            .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo para enviar datos (si aplica)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Asigna el cliente OkHttpClient a Retrofit
            .build()
            .create(NavarraFutbolApi::class.java)
    }
}