package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.adapter.NoticiasAdapter
import com.example.navarrafutbol.model.Noticia
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

/**
 * Actividad que muestra una lista de noticias deportivas obtenidas de una API externa.
 *
 * Funcionalidades:
 * - Recuperar noticias usando OkHttp.
 * - Mostrar un indicador de carga mientras se realiza la solicitud.
 * - Navegación inferior para ir a otras secciones.
 */
class NoticiasActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var progressNoticias: ProgressBar
    private lateinit var bottomNavigation: BottomNavigationView
    private val baseUrl = "https://noticiasapi-16ka.onrender.com/api/"

    /**
     * Inicializa la vista y comienza la carga de noticias.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        // Referencias de vistas
        recycler           = findViewById(R.id.recyclerNoticias)
        progressNoticias   = findViewById(R.id.progressNoticias)
        bottomNavigation   = findViewById(R.id.bottomNavigation)

        recycler.visibility         = View.GONE
        progressNoticias.visibility = View.VISIBLE
        bottomNavigation.selectedItemId = R.id.nav_news

        obtenerNoticias()

        // Configuración de navegación inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> true
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultadosActivity::class.java))
                    true
                }
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

    /**
     * Realiza una solicitud HTTP a la API de noticias y carga la lista en el RecyclerView.
     */
    private fun obtenerNoticias() {
        val client  = OkHttpClient()
        val request = Request.Builder()
            .url("${baseUrl}noticias")
            .build()

        client.newCall(request).enqueue(object : Callback {

            /**
             * Maneja fallos de red mostrando un mensaje de error.
             */
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@NoticiasActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    progressNoticias.visibility = View.GONE
                }
            }

            /**
             * Procesa la respuesta de la API y muestra las noticias en la interfaz.
             */
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val listaNoticias = Gson().fromJson(it, Array<Noticia>::class.java).toList()
                    runOnUiThread {
                        recycler.adapter = NoticiasAdapter(listaNoticias) { noticia ->
                            val intent = Intent(this@NoticiasActivity, DetalleNoticiaActivity::class.java).apply {
                                putExtra("titulo", noticia.titulo)
                                putExtra("autor", noticia.autor)
                                putExtra("contenido", noticia.contenido)
                            }
                            startActivity(intent)
                        }

                        progressNoticias.visibility = View.GONE
                        recycler.visibility         = View.VISIBLE
                    }
                }
            }
        })
    }
}
