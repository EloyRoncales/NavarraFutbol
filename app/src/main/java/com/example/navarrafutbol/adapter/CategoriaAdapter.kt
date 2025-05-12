package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.ViewHolder.CategoriaViewHolder
import com.example.navarrafutbol.model.Categoria

class CategoriaAdapter(categorias: List<Categoria>) : RecyclerView.Adapter<CategoriaViewHolder>() {

    private var categorias: List<Categoria> = categorias
    private var categoriasOriginal: List<Categoria> = categorias.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(categorias[position])
    }

    override fun getItemCount() = categorias.size

    fun filtrarPorEquipo(query: String) {
        if (query.isBlank()) {
            categorias = categoriasOriginal
        } else {
            categorias = categoriasOriginal.map { categoria ->
                val gruposFiltrados = categoria.gruposWrapper.grupos.orEmpty().map { grupo ->
                    val partidosFiltrados = grupo.partidosWrapper.partidos.orEmpty().filter {
                        it.local.nombre.contains(query, true) || it.visitante.nombre.contains(query, true)
                    }
                    grupo.copy(partidosWrapper = grupo.partidosWrapper.copy(partidos = partidosFiltrados))
                }.filter { it.partidosWrapper.partidos.isNotEmpty() }

                categoria.copy(gruposWrapper = categoria.gruposWrapper.copy(grupos = gruposFiltrados))
            }.filter { it.gruposWrapper.grupos.isNotEmpty() }
        }
        notifyDataSetChanged()
    }

    fun setCategorias(categorias: List<Categoria>) {
        this.categorias = categorias
        this.categoriasOriginal = categorias.toList()
        notifyDataSetChanged()
    }
}
