package com.example.navarrafutbol


import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.adapter.ClasificacionAdapter
import com.example.navarrafutbol.model.Clasificacion
import com.example.navarrafutbol.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClasificacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clasificacion)

        val grupoIds = intent.getIntArrayExtra("grupoIds") ?: intArrayOf()
        val nombreCategoria = intent.getStringExtra("nombreCategoria") ?: ""

        findViewById<TextView>(R.id.tituloCategoria).text = nombreCategoria

        for (grupoId in grupoIds) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitClient.api.getClasificaciones().execute()
                val clasificaciones = response.body()?.filter { it.grupoId == grupoId }?.sortedByDescending { it.puntos } ?: emptyList()

                withContext(Dispatchers.Main) {
                    mostrarGrupo(grupoId, clasificaciones)
                }
            }
        }
    }

    private fun mostrarGrupo(grupoId: Int, clasificaciones: List<Clasificacion>) {
        val contenedor = findViewById<LinearLayout>(R.id.contenedorGrupos)
        val inflater = LayoutInflater.from(this)
        val grupoView = inflater.inflate(R.layout.item_grupo_clasificacion, contenedor, false)

        grupoView.findViewById<TextView>(R.id.tituloGrupo).text = "Grupo $grupoId"

        val recyclerView = grupoView.findViewById<RecyclerView>(R.id.recyclerEquipos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ClasificacionAdapter(clasificaciones)

        contenedor.addView(grupoView)
    }

}