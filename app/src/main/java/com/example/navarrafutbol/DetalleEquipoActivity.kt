package com.example.navarrafutbol

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetalleEquipoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_equipo)

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val estadio = intent.getStringExtra("estadio") ?: "Sin estadio"
        val escudoUrl = intent.getStringExtra("escudoUrl") ?: ""

        val nombreText = findViewById<TextView>(R.id.nombreEquipoDetalle)
        val estadioText = findViewById<TextView>(R.id.estadioEquipoDetalle)
        val imageView = findViewById<ImageView>(R.id.escudoEquipoDetalle)

        nombreText.text = nombre
        estadioText.text = estadio

        if (escudoUrl.isNotBlank()) {
            Glide.with(this)
                .load(escudoUrl)
                .placeholder(R.drawable.escudo)
                .error(R.drawable.escudo)
                .into(imageView)
        }
    }
}
