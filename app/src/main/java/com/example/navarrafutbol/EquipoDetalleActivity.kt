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
import kotlinx.coroutines.launch

class EquipoDetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipoDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val equipoId = intent.getIntExtra("equipoId", -1)
        Log.d("EquipoDetalle", "equipoId recibido: $equipoId")
        val nombre = intent.getStringExtra("nombre") ?: "Nombre desconocido"
        val estadio = intent.getStringExtra("estadio") ?: "Estadio desconocido"
        val escudoUrl = intent.getStringExtra("escudoUrl") ?: ""

        binding.textNombre.text = nombre
        binding.textEstadio.text = "Estadio: $estadio"
        Glide.with(this).load(escudoUrl).into(binding.imageEscudo)

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
