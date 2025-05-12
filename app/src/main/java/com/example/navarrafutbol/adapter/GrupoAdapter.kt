package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.ViewHolder.GrupoViewHolder
import com.example.navarrafutbol.model.Grupo

class GrupoAdapter(private val grupos: List<Grupo>) : RecyclerView.Adapter<GrupoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        holder.bind(grupos[position])
    }

    override fun getItemCount() = grupos.size
}