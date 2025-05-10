package com.example.navarrafutbol

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FiltroCategoriasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro_ligas) // Asegúrate de que el archivo XML se llama correctamente

        val btnRegionalPreferente = findViewById<ImageButton>(R.id.btnRegionalPreferente)
        val btnAutonomica = findViewById<ImageButton>(R.id.btnAutonomica)
        val btnTerceraRFEF = findViewById<ImageButton>(R.id.btnTerceraRFEF)

        // Configurar los listeners para los botones, si es necesario
        btnRegionalPreferente.setOnClickListener {
            Toast.makeText(this, "Regional Preferente seleccionada", Toast.LENGTH_SHORT).show()
            // Lógica para manejar la acción
        }

        btnAutonomica.setOnClickListener {
            Toast.makeText(this, "Autonómica seleccionada", Toast.LENGTH_SHORT).show()
            // Lógica para manejar la acción
        }

        btnTerceraRFEF.setOnClickListener {
            Toast.makeText(this, "Tercera RFEF seleccionada", Toast.LENGTH_SHORT).show()
            // Lógica para manejar la acción
        }
    }
}
