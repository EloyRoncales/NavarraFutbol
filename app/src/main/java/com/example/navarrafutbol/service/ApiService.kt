package com.example.navarrafutbol.service


import com.example.navarrafutbol.model.Grupo
import com.example.navarrafutbol.model.Partido
import com.example.navarrafutbol.wrapper.CategoriaWrapper
import com.example.navarrafutbol.wrapper.GrupoWrapper
import com.example.navarrafutbol.wrapper.PartidoWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Obtener todas las categorías
    @GET("categorias")
    fun getCategorias(): Call<CategoriaWrapper>

    // Obtener los grupos de una categoría
    @GET("categorias/{id}/grupos")
    fun getGruposByCategoria(@Path("id") categoriaId: Int): Call<GrupoWrapper>

    // Obtener los partidos de un grupo
    @GET("categorias/grupos/{grupoId}/partidos")
    fun getPartidosByGrupo(@Path("grupoId") grupoId: Int): Call<PartidoWrapper>
}

