package com.example.navarrafutbol.retrofit

import com.example.navarrafutbol.service.NavarraFutbolApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://navarrafutbolapi.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(NavarraFutbolApi::class.java)
}
