package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navarrafutbol.adapter.EquipoAdapter
import com.example.navarrafutbol.databinding.ActivityFavoritosBinding
import com.example.navarrafutbol.model.EquipoFav
import com.example.navarrafutbol.retrofit.RetrofitClient
import com.example.navarrafutbol.service.NavarraFutbolApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * Actividad que muestra los equipos favoritos del usuario autenticado.
 *
 * Funcionalidades:
 * - Carga desde Firestore la lista de IDs de equipos marcados como favoritos.
 * - Consulta los detalles de cada equipo desde la API.
 * - Muestra los equipos en un RecyclerView.
 * - Permite acceder al detalle de cada equipo.
 */
class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private val db = FirebaseFirestore.getInstance()
    private val apiService = RetrofitClient.getInstance().create(NavarraFutbolApi::class.java)
    private lateinit var bottomNavigation: BottomNavigationView

    /**
     * Inicializa la vista y carga la lista de favoritos del usuario actual.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de navegación inferior
        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_favorites

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, NoticiasActivity::class.java))
                    true
                }
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultadosActivity::class.java))
                    true
                }
                R.id.nav_favorites -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Configuración del RecyclerView
        binding.recyclerFavoritos.layoutManager = LinearLayoutManager(this)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Obtener lista de favoritos desde Firebase
        db.collection("users").document(uid).get().addOnSuccessListener { document ->
            val favoritos = (document.get("favoritos") as? List<Long>) ?: emptyList()

            if (favoritos.isEmpty()) {
                Toast.makeText(this, "No tienes equipos favoritos", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            // Obtener información detallada de cada equipo favorito desde la API
            lifecycleScope.launch {
                try {
                    val equiposCompletos = mutableListOf<EquipoFav>()
                    for (id in favoritos) {
                        val equipo = apiService.getEquipo(id.toInt())
                        equipo?.let { equiposCompletos.add(it) }
                    }

                    val adapter = EquipoAdapter(equiposCompletos) { equipo ->
                        val intent = Intent(this@FavoritosActivity, EquipoDetalleActivity::class.java).apply {
                            putExtra("equipoId", equipo.id)
                            putExtra("nombre", equipo.nombre)
                            putExtra("estadio", equipo.estadio)
                            putExtra("escudoUrl", equipo.escudoUrl)
                        }
                        startActivity(intent)
                    }

                    binding.recyclerFavoritos.adapter = adapter

                } catch (e: Exception) {
                    Log.e("FavoritosActivity", "Error cargando equipos favoritos", e)
                    Toast.makeText(this@FavoritosActivity, "Error cargando equipos", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error accediendo a Firebase", Toast.LENGTH_SHORT).show()
        }
    }
}
