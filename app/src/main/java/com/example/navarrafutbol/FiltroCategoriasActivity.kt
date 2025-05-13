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
        val grupoIds = when (categoria) {
            "Regional Preferente" -> intArrayOf(101, 102)
            "Autonómica" -> intArrayOf(103)
            "Tercera RFEF" -> intArrayOf(100)
            else -> intArrayOf()
        }

        val intent = Intent(this, ClasificacionActivity::class.java)
        intent.putExtra("grupoIds", grupoIds)
        intent.putExtra("nombreCategoria", categoria)
        startActivity(intent)
    }
}
