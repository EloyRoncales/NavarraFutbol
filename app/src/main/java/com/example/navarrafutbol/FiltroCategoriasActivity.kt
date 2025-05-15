package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad que permite seleccionar una categoría para ver su clasificación.
 *
 * Al pulsar en una de las opciones (Regional Preferente, Autonómica o Tercera RFEF),
 * se redirige a [ClasificacionActivity] con el ID correspondiente.
 */
class FiltroCategoriasActivity : AppCompatActivity() {

    /**
     * Inicializa los botones de selección de categoría y define sus acciones.
     */
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

    /**
     * Redirige a la pantalla de clasificación enviando el ID y nombre de la categoría.
     *
     * @param categoria Nombre de la categoría seleccionada.
     */
    private fun irAClasificacion(categoria: String) {
        val categoriaId = when (categoria) {
            "Regional Preferente" -> 2
            "Autonómica" -> 3
            "Tercera RFEF" -> 1
            else -> -1
        }

        val intent = Intent(this, ClasificacionActivity::class.java).apply {
            putExtra("categoriaId", categoriaId)
            putExtra("nombreCategoria", categoria)
        }
        startActivity(intent)
    }
}
