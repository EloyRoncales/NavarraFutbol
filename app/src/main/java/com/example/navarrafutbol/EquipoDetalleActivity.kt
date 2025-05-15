package com.example.navarrafutbol

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.navarrafutbol.adapter.JugadorAdapter
import com.example.navarrafutbol.databinding.ActivityEquipoDetalleBinding
import com.example.navarrafutbol.model.Jugador
import com.example.navarrafutbol.retrofit.RetrofitClient
import com.example.navarrafutbol.service.NavarraFutbolApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * Actividad que muestra los detalles de un equipo seleccionado.
 *
 * Incluye:
 * - Nombre, estadio y escudo del equipo.
 * - Lista de jugadores obtenida desde la API.
 * - Posibilidad de añadir o quitar el equipo de favoritos del usuario.
 */
class EquipoDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquipoDetalleBinding

    /**
     * Carga los detalles del equipo desde el intent y la API,
     * e inicializa los controles de favoritos y lista de jugadores.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val equipoId = intent.getIntExtra("equipoId", -1)
        Log.d("EquipoDetalle", "equipoId recibido: $equipoId")
        val nombre = intent.getStringExtra("nombre") ?: "Nombre desconocido"
        val estadio = intent.getStringExtra("estadio") ?: "Estadio desconocido"
        val escudoUrl = intent.getStringExtra("escudoUrl") ?: ""
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val user = auth.currentUser
        val uid = user?.uid

        // Mostrar información general del equipo
        binding.textNombre.text = nombre
        binding.textEstadio.text = "Estadio: $estadio"
        Glide.with(this).load(escudoUrl).into(binding.imageEscudo)

        // Comprobar si el equipo está en favoritos
        uid?.let { userId ->
            val docRef = db.collection("users").document(userId)
            docRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val favoritos = (document.get("favoritos") as? List<Long>) ?: emptyList()
                    val icon = if (favoritos.contains(equipoId.toLong())) {
                        R.drawable.favorito
                    } else {
                        R.drawable.favorito_border
                    }
                    binding.btnFavorito.setImageResource(icon)
                }
            }
        }

        // Añadir o eliminar equipo de favoritos
        binding.btnFavorito.setOnClickListener {
            uid?.let {
                val docRef = db.collection("users").document(it)

                docRef.get().addOnSuccessListener { document ->
                    val favoritos = document.get("favoritos") as? MutableList<Long> ?: mutableListOf()

                    if (favoritos.contains(equipoId.toLong())) {
                        favoritos.remove(equipoId.toLong())
                        binding.btnFavorito.setImageResource(R.drawable.favorito_border)
                        Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                    } else {
                        favoritos.add(equipoId.toLong())
                        binding.btnFavorito.setImageResource(R.drawable.favorito)
                        Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                    }

                    docRef.update("favoritos", favoritos)
                }
            }
        }

        // Configurar RecyclerView de jugadores
        binding.recyclerJugadores.layoutManager = LinearLayoutManager(this)

        if (equipoId != -1) {
            val apiService = RetrofitClient.getInstance().create(NavarraFutbolApi::class.java)
            lifecycleScope.launch {
                try {
                    val jugadores: List<Jugador> = apiService.getJugadoresPorEquipo(equipoId)
                    Log.d("EquipoDetalle", "Jugadores recibidos: ${jugadores.size}")
                    binding.recyclerJugadores.adapter = JugadorAdapter(jugadores)
                } catch (e: Exception) {
                    Log.e("EquipoDetalle", "Error: ${e.message}")
                    Toast.makeText(this@EquipoDetalleActivity, "Error cargando jugadores", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "ID de equipo inválido", Toast.LENGTH_SHORT).show()
        }
    }
}
