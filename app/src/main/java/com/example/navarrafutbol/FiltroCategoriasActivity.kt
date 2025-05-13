package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FiltroCategoriasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro_ligas)

        val btnRegionalPreferente = findViewById<ImageButton>(R.id.btnRegionalPreferente)
        val btnAutonomica = findViewById<ImageButton>(R.id.btnAutonomica)
        val btnTerceraRFEF = findViewById<ImageButton>(R.id.btnTerceraRFEF)

        btnRegionalPreferente.setOnClickListener {
            irAClasificacion("Regional Preferente")
        }

        btnAutonomica.setOnClickListener {
            irAClasificacion("Autonómica")
        }

        btnTerceraRFEF.setOnClickListener {
            irAClasificacion("Tercera RFEF")
        }
    }

    private fun irAClasificacion(categoria: String) {
        val categoriaId = when (categoria) {
            "Regional Preferente" -> 2
            "Autonómica" -> 3
            "Tercera RFEF" -> 1
            else -> -1
        }

        val intent = Intent(this, ClasificacionActivity::class.java)
        intent.putExtra("categoriaId", categoriaId)
        intent.putExtra("nombreCategoria", categoria) // opcional, si quieres mostrar el nombre
        startActivity(intent)
    }

}
