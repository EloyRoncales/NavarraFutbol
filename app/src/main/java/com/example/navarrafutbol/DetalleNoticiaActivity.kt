package com.example.navarrafutbol

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleNoticiaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_noticia)

        val titulo = intent.getStringExtra("titulo")
        val contenido = intent.getStringExtra("contenido")
        val autor = intent.getStringExtra("autor")
        val fecha = intent.getStringExtra("fecha")

        findViewById<TextView>(R.id.tituloDetalle).text = titulo
        findViewById<TextView>(R.id.autorDetalle).text = "Por " + autor
        findViewById<TextView>(R.id.fechaDetalle).text = fecha?.substringBefore("T")?.replace("-", "/")
        findViewById<TextView>(R.id.contenidoDetalle).text = contenido
    }
}
