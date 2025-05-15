package com.example.navarrafutbol

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad que muestra los detalles de una noticia seleccionada.
 *
 * Recibe información desde un intent:
 * - Título
 * - Contenido
 * - Autor
 * - Fecha
 *
 * Y los presenta en pantalla.
 */
class DetalleNoticiaActivity : AppCompatActivity() {

    /**
     * Recupera los datos de la noticia desde el intent y los muestra en la interfaz.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_noticia)

        val titulo = intent.getStringExtra("titulo")
        val contenido = intent.getStringExtra("contenido")
        val autor = intent.getStringExtra("autor")
        val fecha = intent.getStringExtra("fecha")

        findViewById<TextView>(R.id.tituloDetalle).text = titulo
        findViewById<TextView>(R.id.autorDetalle).text = "Por $autor"
        findViewById<TextView>(R.id.fechaDetalle).text = fecha?.substringBefore("T")?.replace("-", "/")
        findViewById<TextView>(R.id.contenidoDetalle).text = contenido
    }
}
