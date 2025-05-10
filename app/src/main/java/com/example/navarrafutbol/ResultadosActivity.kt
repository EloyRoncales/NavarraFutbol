package com.example.navarrafutbol

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.adapter.GrupoAdapter
import com.example.navarrafutbol.model.Categoria
import com.example.navarrafutbol.model.Partido
import com.example.navarrafutbol.model.Grupo
import com.example.navarrafutbol.service.ApiService
import com.example.navarrafutbol.wrapper.CategoriaWrapper
import com.example.navarrafutbol.wrapper.GrupoWrapper
import com.example.navarrafutbol.wrapper.PartidoWrapper
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ResultadosActivity : AppCompatActivity() {

    private lateinit var recyclerViewGrupos: RecyclerView
    private lateinit var grupoAdapter: GrupoAdapter
    private lateinit var categoriaList: List<Categoria>
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var filtroImageView: ImageView
    private lateinit var tvNoHayResultados: TextView
    private lateinit var apiService: ApiService // Declarar ApiService

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        recyclerViewGrupos = findViewById(R.id.rvCategorias)
        recyclerViewGrupos.layoutManager = LinearLayoutManager(this)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_results
        filtroImageView = findViewById(R.id.filtroImageView)
        tvNoHayResultados = findViewById(R.id.tvNoHayResultados)

        grupoAdapter = GrupoAdapter(emptyList())
        recyclerViewGrupos.adapter = grupoAdapter

        filtroImageView.setOnClickListener {
            val intent = Intent(this, FiltroCategoriasActivity::class.java)
            startActivity(intent)
        }

        Log.d("ResultadosActivity", "RecyclerView encontrado: $recyclerViewGrupos")

        // Inicializar Retrofit y ApiService en onCreate
        val logging = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(logging))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://navarrafutbolapi.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        apiService = retrofit.create(ApiService::class.java) // Inicializar apiService

        loadCategoriasFromApi()

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, FiltroCategoriasActivity::class.java))
                    true
                }
                R.id.nav_results -> {
                    true
                }
                R.id.nav_favorites -> {
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadCategoriasFromApi() {
        apiService.getCategorias().enqueue(object : Callback<CategoriaWrapper> {  // Cambiado a CategoriasResponse
            override fun onResponse(call: Call<CategoriaWrapper>, response: Response<CategoriaWrapper>) {
                Log.d("API Response", "Código de respuesta: ${response.code()}, URL: ${call.request().url}")
                if (response.isSuccessful) {
                    val categorias = response.body()?.categorias ?: emptyList() // Cambiado de List<Categoria> a categorias de CategoriasResponse
                    Log.d("ResultadosActivity", "Categorías cargadas correctamente: ${categorias.size}")
                    Log.d("ResultadosActivity", "Categorías response: ${response.body()}")
                    if (categorias.isEmpty()) {
                        Log.d("ResultadosActivity", "La lista de categorías está vacía.")
                        tvNoHayResultados.text = "No hay resultados disponibles."
                        tvNoHayResultados.visibility = View.VISIBLE
                        recyclerViewGrupos.visibility = View.GONE
                    } else {
                        Log.d("ResultadosActivity", "Categorías obtenidas: $categorias")
                        // Ahora llamamos a loadGruposDeCategoria para cada categoría
                        for (categoria in categorias) {
                            loadGruposDeCategoria(categoria.id)
                        }
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        404 -> {
                            Log.e("API Error", "Error 404: No se encontraron los datos.")
                            "No se encontraron los datos. Verifica la URL de la API."
                        }
                        500 -> "Error interno del servidor. Inténtalo de nuevo más tarde."
                        else -> "Error al cargar los datos. Código: ${response.code()}"
                    }
                    tvNoHayResultados.text = errorMessage
                    tvNoHayResultados.visibility = View.VISIBLE
                    recyclerViewGrupos.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<CategoriaWrapper>, t: Throwable) {
                Log.e("ResultadosActivity", "Error en la llamada a la API: ${t.message}, URL: ${call.request().url}")
                tvNoHayResultados.text = "Error de conexión: ${t.message}"
                tvNoHayResultados.visibility = View.VISIBLE
                recyclerViewGrupos.visibility = View.GONE
            }
        })
    }


    private fun loadGruposDeCategoria(categoriaId: Int) {
        apiService.getGruposByCategoria(categoriaId).enqueue(object : Callback<GrupoWrapper> {
            override fun onResponse(call: Call<GrupoWrapper>, response: Response<GrupoWrapper>) {
                Log.d("Grupos Response", "Código de respuesta: ${response.code()}, URL: ${call.request().url}")
                if (response.isSuccessful) {
                    val grupos = response.body()?.grupos ?: emptyList()
                    Log.d("ResultadosActivity", "Grupos obtenidos para la categoría $categoriaId: ${grupos.size}")

                    grupoAdapter = GrupoAdapter(grupos)
                    recyclerViewGrupos.adapter = grupoAdapter
                    grupoAdapter.notifyDataSetChanged()

                    loadPartidosDeGrupos(grupos)
                } else {
                    val errorMessage = when (response.code()) {
                        404 -> {
                            Log.e("API Error", "Error 404: No se encontraron los datos.")
                            "No se encontraron los datos. Verifica la URL de la API."
                        }
                        500 -> "Error interno del servidor. Inténtalo de nuevo más tarde."
                        else -> "Error al cargar los datos. Código: ${response.code()}"
                    }
                    tvNoHayResultados.text = errorMessage
                    tvNoHayResultados.visibility = View.VISIBLE
                    recyclerViewGrupos.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<GrupoWrapper>, t: Throwable) {
                Log.e("ResultadosActivity", "Error al cargar grupos de la categoría $categoriaId: ${t.message}, URL: ${call.request().url}")
                tvNoHayResultados.text = "Error de conexión: ${t.message}"
                tvNoHayResultados.visibility = View.VISIBLE
                recyclerViewGrupos.visibility = View.GONE
            }
        })
    }


    private fun loadPartidosDeGrupos(grupos: List<Grupo>) {
        for (grupo in grupos) {
            apiService.getPartidosByGrupo(grupo.id).enqueue(object : Callback<PartidoWrapper> {
                override fun onResponse(call: Call<PartidoWrapper>, response: Response<PartidoWrapper>) {
                    Log.d("Partidos Response", "Grupo ID: ${grupo.id}, Código de respuesta: ${response.code()}, URL: ${call.request().url}")
                    if (response.isSuccessful) {
                        // Aquí accedemos a la lista de partidos dentro del wrapper
                        val partidos = response.body()?.partidos ?: emptyList()
                        Log.d("ResultadosActivity", "Partidos del grupo ${grupo.id}: ${partidos.size}")
                        // Aquí puedes actualizar la UI o almacenar los partidos como necesites.
                    } else {
                        Log.e("ResultadosActivity", "Fallo al obtener partidos del grupo ${grupo.id}, código: ${response.code()}")
                        // Opcional: mostrar mensaje breve sin ocultar la UI global
                        Toast.makeText(
                            this@ResultadosActivity,
                            "No se pudieron cargar los partidos del grupo ${grupo.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PartidoWrapper>, t: Throwable) {
                    Log.e("ResultadosActivity", "Error al cargar partidos del grupo ${grupo.id}: ${t.message}, URL: ${call.request().url}")
                    // También solo mostrar mensaje breve si falla, sin ocultar vista principal
                    Toast.makeText(
                        this@ResultadosActivity,
                        "Error al conectar con los partidos del grupo ${grupo.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

}

