package com.example.navarrafutbol.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Grupo

class GrupoAdapter(private val grupos: List<Grupo>) : RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = grupos[position]
        holder.bind(grupo)
    }

    override fun getItemCount(): Int = grupos.size

    inner class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreGrupo: TextView = itemView.findViewById(R.id.tvGrupo)
        private val recyclerViewPartidos: RecyclerView = itemView.findViewById(R.id.rvPartidos)

        fun bind(grupo: Grupo) {
            nombreGrupo.text = grupo.nombre
            // Configuramos el RecyclerView para los partidos de este grupo
            val partidoAdapter = PartidoAdapter(grupo.partidos) // Asume que grupo.partidos tiene datos
            recyclerViewPartidos.layoutManager = LinearLayoutManager(itemView.context)
            recyclerViewPartidos.adapter = partidoAdapter

            Log.d("GrupoAdapter", "Grupo: ${grupo.nombre}, Partidos: ${grupo.partidos.size}")
        }
    }
}


