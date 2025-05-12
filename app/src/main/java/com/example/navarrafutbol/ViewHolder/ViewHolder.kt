package com.example.navarrafutbol.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.adapter.*
import com.example.navarrafutbol.model.*


class PartidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvEquipoLocal: TextView = itemView.findViewById(R.id.tvEquipoLocal)
    private val tvEquipoVisitante: TextView = itemView.findViewById(R.id.tvEquipoVisitante)
    private val tvHora: TextView = itemView.findViewById(R.id.tvHora)
    private val ivEscudoLocal: ImageView = itemView.findViewById(R.id.ivEscudoLocal)
    private val ivEscudoVisitante: ImageView = itemView.findViewById(R.id.ivEscudoVisitante)

    fun bind(partido: Partido) {
        tvEquipoLocal.text = partido.local.nombre
        tvEquipoVisitante.text = partido.visitante.nombre
        tvHora.text = partido.fecha.split("T").getOrNull(1)?.substring(0, 5) ?: "--:--"
        // Puedes usar Glide para cargar logos si tienes URLs
    }
}


class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvGrupo: TextView = itemView.findViewById(R.id.tvGrupo)
    private val rvPartidos: RecyclerView = itemView.findViewById(R.id.rvPartidos)

    fun bind(grupo: Grupo) {
        tvGrupo.text = grupo.grupo
        rvPartidos.layoutManager = LinearLayoutManager(itemView.context)
        val partidos = grupo.partidosWrapper?.partidos.orEmpty()
        rvPartidos.adapter = PartidoAdapter(partidos)
    }

}


class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoria)
    private val rvGrupos: RecyclerView = itemView.findViewById(R.id.rvGrupos)

    fun bind(categoria: Categoria) {
        tvCategoria.text = categoria.categoria
        rvGrupos.layoutManager = LinearLayoutManager(itemView.context)

        val grupos = categoria.gruposWrapper.grupos.orEmpty()
        rvGrupos.adapter = GrupoAdapter(grupos)
    }
}
