// src/main/java/com/example/navarrafutbol/ResultadosActivity.kt
package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.adapter.CategoriaAdapter
import com.example.navarrafutbol.model.Categoria
import com.example.navarrafutbol.retrofit.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ResultadosActivity : AppCompatActivity() {

    private lateinit var rvResultados: RecyclerView
    private lateinit var svBuscar: SearchView
    private var todasCategorias: List<Categoria> = emptyList()
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        rvResultados = findViewById(R.id.rvResultados)
        svBuscar = findViewById(R.id.svBuscarEquipo)
        rvResultados.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            // Lista de IDs de categorías que quieres mostrar
            val categoryIds = listOf(1, 2)
            // Lanzamos todas las peticiones en paralelo
            val deferred = categoryIds.map { id ->
                async { RetrofitClient.api.getPartidos(id) }
            }
            todasCategorias = deferred.awaitAll()
            rvResultados.adapter = CategoriaAdapter(todasCategorias)
        }

        svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(newText: String): Boolean {
                val filtradas = todasCategorias.map { cat ->
                    cat.copy(
                        Grupos = cat.Grupos.map { g ->
                            g.copy(
                                Partidos = g.Partidos.filter { p ->
                                    p.EquipoLocal.contains(newText, true) ||
                                            p.EquipoVisitante.contains(newText, true)
                                }
                            )
                        }.filter { it.Partidos.isNotEmpty() }
                    )
                }.filter { it.Grupos.isNotEmpty() }

                rvResultados.adapter = CategoriaAdapter(filtradas)
                return true
            }
        })

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_results

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, NoticiasActivity::class.java))
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
}
