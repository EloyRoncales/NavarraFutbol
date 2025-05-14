package com.example.navarrafutbol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var nombreUsuario: TextView
    private lateinit var correo: TextView
    private lateinit var telefonoUsuario: TextView
    private lateinit var btnEditarTelefono: Button
    private lateinit var btnCambiarPassword: Button
    private lateinit var btnLogout: Button
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        nombreUsuario = findViewById(R.id.nombreUsuario)
        correo = findViewById(R.id.correoElectronico)
        telefonoUsuario = findViewById(R.id.telefonoUsuario)
        btnEditarTelefono = findViewById(R.id.btnEditarTelefono)
        btnCambiarPassword = findViewById(R.id.btnCambiarPassword)
        btnLogout = findViewById(R.id.btnLogout)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_profile

        cargarDatosUsuario()

        btnEditarTelefono.setOnClickListener {
            mostrarDialogoEditarTelefono()
        }

        btnCambiarPassword.setOnClickListener {
            mostrarDialogoReautenticar()
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("rememberMe", false)
            editor.putBoolean("sessionActive", false)
            editor.remove("email")
            editor.remove("password")
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, NoticiasActivity::class.java))
                    true
                }
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultadosActivity::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritosActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarDatosUsuario() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            database.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username")
                        val phone = document.getString("phone")

                        nombreUsuario.text = username ?: currentUser.displayName ?: "Usuario"
                        correo.text = currentUser.email ?: "Correo no disponible"
                        telefonoUsuario.text = if (!phone.isNullOrEmpty()) formatPhoneNumber(phone) else "No disponible"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("PerfilActivity", "Error al cargar datos de Firestore: ${exception.message}")
                    nombreUsuario.text = currentUser.displayName ?: "Usuario"
                    correo.text = currentUser.email ?: "Correo no disponible"
                    telefonoUsuario.text = "No disponible"
                }
        } else {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


    private fun formatPhoneNumber(phoneNumber: String): String {
        return if (phoneNumber.length > 4) {
            phoneNumber.take(4) + "*".repeat(phoneNumber.length - 4)
        } else {
            phoneNumber
        }
    }


    private fun mostrarDialogoEditarTelefono() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar número de teléfono")

        val input = EditText(this)
        input.hint = "Nuevo número de teléfono"
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoTelefono = input.text.toString().trim()
            if (nuevoTelefono.isNotEmpty()) {
                actualizarTelefonoEnFirestore(nuevoTelefono)
            } else {
                Toast.makeText(this, "El número de teléfono no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun actualizarTelefonoEnFirestore(nuevoTelefono: String) {
        auth.currentUser?.uid?.let { userId ->
            database.collection("users").document(userId)
                .update("phone", nuevoTelefono)
                .addOnSuccessListener {
                    Toast.makeText(this, "Número de teléfono actualizado", Toast.LENGTH_SHORT).show()
                    cargarDatosUsuario()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al actualizar el teléfono: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("PerfilActivity", "Error al actualizar teléfono", e)
                }
        }
    }

    private fun mostrarDialogoReautenticar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reautenticar")
        builder.setMessage("Para cambiar la contraseña, debes iniciar sesión nuevamente.")

        val inputLayout = LinearLayout(this)
        inputLayout.orientation = LinearLayout.VERTICAL
        val emailInput = EditText(this)
        emailInput.hint = "Correo electrónico"
        val passwordInput = EditText(this)
        passwordInput.hint = "Contraseña actual"

        inputLayout.addView(emailInput)
        inputLayout.addView(passwordInput)
        builder.setView(inputLayout)

        builder.setPositiveButton("Confirmar") { _, _ ->
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                reautenticarUsuario(email, password)
            } else {
                Toast.makeText(this, "Introduce correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun reautenticarUsuario(email: String, password: String) {
        val user = auth.currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mostrarDialogoCambiarPassword()
                } else {
                    Toast.makeText(this, "Error al reautenticar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    Log.e("PerfilActivity", "Error al reautenticar", task.exception)
                }
            }
    }

    private fun mostrarDialogoCambiarPassword() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cambiar contraseña")

        val inputLayout = LinearLayout(this)
        inputLayout.orientation = LinearLayout.VERTICAL
        val newPasswordInput = EditText(this)
        newPasswordInput.hint = "Nueva contraseña"
        val confirmNewPasswordInput = EditText(this)
        confirmNewPasswordInput.hint = "Confirmar nueva contraseña"

        inputLayout.addView(newPasswordInput)
        inputLayout.addView(confirmNewPasswordInput)
        builder.setView(inputLayout)

        builder.setPositiveButton("Guardar") { _, _ ->
            val newPassword = newPasswordInput.text.toString()
            val confirmNewPassword = confirmNewPasswordInput.text.toString()

            if (newPassword.isNotEmpty() && newPassword == confirmNewPassword) {
                cambiarPassword(newPassword)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden o están vacías", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun cambiarPassword(newPassword: String) {
        val user = auth.currentUser

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                    // Opcional: Volver a iniciar sesión o cerrar sesión para seguridad
                } else {
                    Toast.makeText(this, "Error al actualizar la contraseña: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    Log.e("PerfilActivity", "Error al actualizar la contraseña", task.exception)
                }
            }
    }
}