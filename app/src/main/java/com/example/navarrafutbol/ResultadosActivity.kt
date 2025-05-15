package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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

/**
 * Actividad que muestra los resultados de partidos organizados por categorías.
 *
 * Esta pantalla:
 * - Recupera los partidos desde la API REST mediante Retrofit.
 * - Permite filtrar equipos en tiempo real mediante un `SearchView`.
 * - Ofrece navegación inferior a otras secciones de la app.
 */
class ResultadosActivity : AppCompatActivity() {

    private lateinit var rvResultados: RecyclerView
    private lateinit var progressResultados: ProgressBar
    private lateinit var svBuscar: SearchView
    private lateinit var bottomNavigation: BottomNavigationView
    private var todasCategorias: List<Categoria> = emptyList()

    /**
     * Método principal donde se inicializa la interfaz, se recuperan los datos
     * y se configura la lógica de filtrado y navegación inferior.
     *
     * @param savedInstanceState Estado previamente guardado (si aplica).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        // Inicializa vistas
        rvResultados         = findViewById(R.id.rvResultados)
        progressResultados   = findViewById(R.id.progressResultados)
        svBuscar             = findViewById(R.id.svBuscarEquipo)
        bottomNavigation     = findViewById(R.id.bottomNavigation)

        // Filtro por categorías
        val ivFiltro = findViewById<ImageView>(R.id.podioImageView)
        ivFiltro.setOnClickListener {
            startActivity(Intent(this, FiltroCategoriasActivity::class.java))
        }

        // Configuración inicial
        rvResultados.layoutManager = LinearLayoutManager(this)
        rvResultados.visibility    = View.GONE
        progressResultados.visibility = View.VISIBLE
        bottomNavigation.selectedItemId = R.id.nav_results

        // Llamada a la API usando corrutinas
        lifecycleScope.launch {
            val categoryIds = listOf(1, 2, 3)
            val deferred    = categoryIds.map { id -> async { RetrofitClient.api.getPartidos(id) } }
            todasCategorias = deferred.awaitAll()

            rvResultados.adapter = CategoriaAdapter(todasCategorias)
            progressResultados.visibility = View.GONE
            rvResultados.visibility       = View.VISIBLE
        }

        // Filtro de búsqueda en tiempo real
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

        // Navegación inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, NoticiasActivity::class.java))
                    true
                }
                R.id.nav_results -> true
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritosActivity::class.java))
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
