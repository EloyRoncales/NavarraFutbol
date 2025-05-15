package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Actividad de lanzamiento que determina si el usuario ya está autenticado.
 *
 * Si el usuario tiene una sesión iniciada, redirige a la pantalla principal de resultados.
 * Si no, redirige a la pantalla de inicio de sesión.
 *
 * Esta actividad no tiene UI visible y se cierra inmediatamente después de redirigir.
 */
class LauncherActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    /**
     * Comprueba el estado de autenticación del usuario y redirige a la actividad correspondiente.
     */
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

        // Cierra esta actividad para que no quede en la pila de navegación
        finish()
    }
}
