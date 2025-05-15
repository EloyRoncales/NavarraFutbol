package com.example.navarrafutbol

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navarrafutbol.adapter.ClasificacionPadreAdapter
import com.example.navarrafutbol.databinding.ActivityClasificacionBinding
import com.example.navarrafutbol.retrofit.RetrofitClient
import com.example.navarrafutbol.service.NavarraFutbolApi
import kotlinx.coroutines.launch

/**
 * Actividad que muestra la clasificación de los equipos de una categoría específica.
 *
 * Recibe el `categoriaId` desde un intent y consulta los grupos y clasificaciones
 * desde una API REST. Los datos se muestran en un `RecyclerView` utilizando
 * `ClasificacionPadreAdapter`.
 */
class ClasificacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClasificacionBinding
    private lateinit var apiService: NavarraFutbolApi
    private lateinit var adapter: ClasificacionPadreAdapter

    /**
     * Inicializa la actividad y lanza la consulta de clasificación según la categoría recibida.
     */
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

    /**
     * Consulta la clasificación desde la API según la categoría seleccionada.
     *
     * @param categoriaId ID de la categoría a consultar.
     */
    private fun obtenerClasificacion(categoriaId: Int) {
        lifecycleScope.launch {
            try {
                val listaGrupos = apiService.getClasificacionPorCategoria(categoriaId)

                if (listaGrupos.isEmpty()) {
                    Toast.makeText(this@ClasificacionActivity, "Sin datos", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                adapter = ClasificacionPadreAdapter(listaGrupos)
                binding.recyclerView.adapter = adapter

                // Si hay varios grupos, puedes usar un Expandable RecyclerView para mostrarlos todos.
            } catch (e: Exception) {
                Log.e("ClasificacionActivity", "Error cargando clasificación: ${e.message}")
                Toast.makeText(this@ClasificacionActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
