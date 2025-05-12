package com.example.navarrafutbol

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.ViewModel.ResultadosViewModel
import com.example.navarrafutbol.adapter.CategoriaAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResultadosActivity : AppCompatActivity() {
    private val viewModel: ResultadosViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CategoriaAdapter
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        recyclerView = findViewById(R.id.rvResultados)
        searchView = findViewById(R.id.svBuscarEquipo)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CategoriaAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.categorias.observe(this) { categorias ->
            adapter.setCategorias(categorias)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it ?: "Error desconocido", Toast.LENGTH_LONG).show()
        }

        viewModel.cargarDatos()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filtrarPorEquipo(newText ?: "")
                return true
            }
        })

        bottomNavigation.selectedItemId = R.id.nav_results

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    startActivity(Intent(this, NoticiasActivity::class.java))
                    true
                }
                R.id.nav_results -> true
                R.id.nav_favorites -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
