package com.example.navarrafutbol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad de inicio de sesión del usuario.
 *
 * Permite:
 * - Autenticación con correo y contraseña.
 * - Inicio de sesión con Google.
 * - Recordar la sesión usando SharedPreferences.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoToRegister: Button
    private lateinit var cbRememberMe: CheckBox
    private val PREF_REMEMBER_ME = "rememberMe"
    private val PREF_EMAIL = "email"
    private val PREF_PASSWORD = "password"
    private val PREF_SESSION_ACTIVE = "sessionActive"

    /**
     * Inicializa la vista, comprueba sesión guardada y configura autenticación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoToRegister = findViewById(R.id.btnGoToRegister)
        cbRememberMe = findViewById(R.id.cbRememberMe)

        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val rememberMeChecked = sharedPreferences.getBoolean(PREF_REMEMBER_ME, false)
        cbRememberMe.isChecked = rememberMeChecked

        // Autologin si el usuario ya está guardado
        if (rememberMeChecked && sharedPreferences.getBoolean(PREF_SESSION_ACTIVE, false)) {
            val storedEmail = sharedPreferences.getString(PREF_EMAIL, "")
            val storedPassword = sharedPreferences.getString(PREF_PASSWORD, "")
            if (!storedEmail.isNullOrEmpty() && !storedPassword.isNullOrEmpty()) {
                auth.signInWithEmailAndPassword(storedEmail, storedPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, ResultadosActivity::class.java))
                            finish()
                        } else {
                            Log.w("LoginActivity", "Auto signIn failed", task.exception)
                            sharedPreferences.edit().putBoolean(PREF_SESSION_ACTIVE, false).apply()
                        }
                    }
            }
        } else if (auth.currentUser != null && sharedPreferences.getBoolean(PREF_SESSION_ACTIVE, false)) {
            startActivity(Intent(this, ResultadosActivity::class.java))
            finish()
        }

        // Ir a pantalla de registro
        btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Inicio de sesión con correo y contraseña
        btnLogin.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString()
            val rememberMe = cbRememberMe.isChecked

            if (email.isEmpty()) {
                etUsername.error = "El correo electrónico es requerido"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "La contraseña es requerida"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean(PREF_REMEMBER_ME, rememberMe)
                        editor.putBoolean(PREF_SESSION_ACTIVE, true)
                        if (rememberMe) {
                            editor.putString(PREF_EMAIL, email)
                            editor.putString(PREF_PASSWORD, password)
                        } else {
                            editor.remove(PREF_EMAIL)
                            editor.remove(PREF_PASSWORD)
                        }
                        editor.apply()

                        startActivity(Intent(this, ResultadosActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Fallo en la autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    }
                }
        }

        // Configurar Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Botón para iniciar sesión con Google
        findViewById<LinearLayout>(R.id.btnGoogleSignIn).setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    /**
     * Maneja el resultado del intent de Google Sign-In.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error en el inicio de sesión con Google", Toast.LENGTH_SHORT).show()
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    /**
     * Autentica en Firebase usando la cuenta de Google del usuario.
     *
     * @param idToken Token recibido tras el login con Google.
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean(PREF_REMEMBER_ME, true)
                    editor.putBoolean(PREF_SESSION_ACTIVE, true)
                    editor.apply()

                    val db = FirebaseFirestore.getInstance()

                    // Crear el documento del usuario si no existe
                    db.collection("users").document(user!!.uid).get()
                        .addOnSuccessListener { document ->
                            if (!document.exists()) {
                                val userMap = hashMapOf(
                                    "username" to (user.displayName ?: "Usuario"),
                                    "email" to (user.email ?: ""),
                                    "phone" to "",
                                    "favoritos" to emptyList<Long>()
                                )
                                db.collection("users").document(user.uid)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        Log.d("LoginActivity", "Documento de usuario creado exitosamente")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("LoginActivity", "Error al crear documento del usuario", e)
                                    }
                            }
                            startActivity(Intent(this, ResultadosActivity::class.java))
                            finish()
                        }
                } else {
                    Toast.makeText(this, "Fallo en autenticación con Firebase", Toast.LENGTH_SHORT).show()
                    Log.w("LoginActivity", "firebaseAuthWithGoogle - Authentication failed", task.exception)
                }
            }
    }

    /**
     * Redirige automáticamente si el usuario ya tiene una sesión activa.
     */
    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(PREF_SESSION_ACTIVE, false) && auth.currentUser != null) {
            startActivity(Intent(this, ResultadosActivity::class.java))
            finish()
        }
    }
}
