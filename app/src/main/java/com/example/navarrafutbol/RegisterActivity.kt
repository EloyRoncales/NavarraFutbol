package com.example.navarrafutbol

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad encargada del registro de nuevos usuarios.
 *
 * Esta pantalla:
 * - Recoge los datos del formulario de registro (correo, nombre, teléfono y contraseña).
 * - Valida los campos ingresados y confirma la aceptación de términos.
 * - Registra al usuario en Firebase Authentication.
 * - Guarda la información adicional en Firestore.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    /**
     * Inicializa la interfaz de usuario y configura el botón de registro.
     *
     * @param savedInstanceState Estado anterior de la actividad (si existe).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicialización de vistas
        etEmail = findViewById(R.id.etRegisterEmail)
        etUsername = findViewById(R.id.etRegisterUsername)
        etPhone = findViewById(R.id.etRegisterPhone)
        etPassword = findViewById(R.id.etRegisterPassword)
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPassword)
        cbTerms = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegisterSubmit)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Acción del botón de registro
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Validaciones
            if (email.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Acepta los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registro con Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val userMap = hashMapOf(
                                "username" to username,
                                "email" to email,
                                "phone" to phone,
                                "favoritos" to emptyList<Long>()
                            )
                            // Guardar datos adicionales en Firestore
                            db.collection("users").document(user.uid)
                                .set(userMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registro exitoso y datos guardados", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error al guardar datos: ${e.message}", Toast.LENGTH_LONG).show()
                                    Log.e("RegisterActivity", "Error al guardar datos", e)
                                }
                        }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
