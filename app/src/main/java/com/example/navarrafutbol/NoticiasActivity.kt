package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.adapter.NoticiasAdapter
import com.example.navarrafutbol.model.Noticia
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class NoticiasActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private val baseUrl = "https://noticiasapi-16ka.onrender.com/api/"
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        recycler = findViewById(R.id.recyclerNoticias)

        obtenerNoticias()

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    true
                }
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultadosActivity::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun obtenerNoticias() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${baseUrl}noticias")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@NoticiasActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val listaNoticias = Gson().fromJson(it, Array<Noticia>::class.java).toList()
                    runOnUiThread {
                        recycler.adapter = NoticiasAdapter(listaNoticias) { noticia ->
                            val intent = Intent(this@NoticiasActivity, DetalleNoticiaActivity::class.java)
                            intent.putExtra("titulo", noticia.titulo)
                            intent.putExtra("autor", noticia.autor)
                            intent.putExtra("contenido", noticia.contenido)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}