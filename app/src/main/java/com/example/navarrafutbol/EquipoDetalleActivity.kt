package com.example.navarrafutbol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.navarrafutbol.databinding.ActivityEquipoDetalleBinding

class EquipoDetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipoDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre") ?: "Nombre desconocido"
        val estadio = intent.getStringExtra("estadio") ?: "Estadio desconocido"
        val escudoUrl = intent.getStringExtra("escudoUrl") ?: ""

        binding.textNombre.text = nombre
        binding.textEstadio.text = "Estadio: $estadio"
        Glide.with(this).load(escudoUrl).into(binding.imageEscudo)
    }
}
