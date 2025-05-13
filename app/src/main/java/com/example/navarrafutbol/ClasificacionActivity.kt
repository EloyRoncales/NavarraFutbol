package com.example.navarrafutbol

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navarrafutbol.adapter.*
import com.example.navarrafutbol.databinding.ActivityClasificacionBinding
import com.example.navarrafutbol.retrofit.RetrofitClient
import com.example.navarrafutbol.service.NavarraFutbolApi
import kotlinx.coroutines.launch
import android.util.Log


class ClasificacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClasificacionBinding
    private lateinit var apiService: NavarraFutbolApi
    private lateinit var adapter: ClasificacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClasificacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoriaId = intent.getIntExtra("categoriaId", -1)
        if (categoriaId == -1) {
            Toast.makeText(this, "Categoría inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        apiService = RetrofitClient.getInstance().create(NavarraFutbolApi::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerClasificacion(categoriaId)
    }

    private fun obtenerClasificacion(categoriaId: Int) {
        lifecycleScope.launch {
            try {
                val listaGrupos = apiService.getClasificacionPorCategoria(categoriaId)

                if (listaGrupos.isEmpty()) {
                    Toast.makeText(this@ClasificacionActivity, "Sin datos", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Si solo quieres mostrar el primer grupo:
                val grupo = listaGrupos.first()
                adapter = ClasificacionAdapter(grupo.clasificaciones)
                binding.recyclerView.adapter = adapter

                // Si quieres mostrar todos los grupos, se requiere adaptar a varios RecyclerView o un Expandable
            } catch (e: Exception) {
                Log.e("ClasificacionActivity", "Error cargando clasificación: ${e.message}")
                Toast.makeText(this@ClasificacionActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

