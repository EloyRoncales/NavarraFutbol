package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LauncherActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            // Usuario ya autenticado
            startActivity(Intent(this, ResultadosActivity::class.java))
        } else {
            // No ha iniciado sesión
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // Cierra esta actividad para que no se quede en back stack




    }
}
