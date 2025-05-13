package com.example.navarrafutbol.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navarrafutbol.EquipoDetalleActivity
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.ClasificacionItem
import com.example.navarrafutbol.response.CategoriaClasificacionResponse

class ClasificacionAdapter(private val lista: List<ClasificacionItem>) :
    RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClasificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clasificacion, parent, false)
        return ClasificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClasificacionViewHolder, position: Int) {
        holder.bind(lista[position], position, lista.size)
    }

    override fun getItemCount(): Int = lista.size

    class ClasificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val equipoNombre: TextView = itemView.findViewById(R.id.textEquipoNombre)
        private val puntos: TextView = itemView.findViewById(R.id.textPuntos)
        private val partidos: TextView = itemView.findViewById(R.id.textPartidosJugados)
        private val escudo: ImageView = itemView.findViewById(R.id.imageEscudo)

        fun bind(item: ClasificacionItem, position: Int, totalItems: Int) {
            equipoNombre.text = item.equipo.nombre
            puntos.text = "Pts: ${item.puntos}"
            partidos.text = "PJ: ${item.partidosJugados}"
            Glide.with(itemView.context).load(item.equipo.escudoUrl).into(escudo)

            val contexto = itemView.context
            val fondoVerde = contexto.getColor(R.color.verde_resaltado)
            val fondoRojo = contexto.getColor(R.color.rojo_resaltado)
            val fondoNormal = contexto.getColor(android.R.color.transparent)

            itemView.setBackgroundColor(
                when {
                    position == 0 -> fondoVerde
                    position >= totalItems - 3 -> fondoRojo
                    else -> fondoNormal
                }
            )

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, EquipoDetalleActivity::class.java)
                intent.putExtra("nombre", item.equipo.nombre)
                intent.putExtra("estadio", item.equipo.estadio)
                intent.putExtra("escudoUrl", item.equipo.escudoUrl)
                context.startActivity(intent)
            }
        }

    }
}

class ClasificacionPadreAdapter(private val grupos: List<CategoriaClasificacionResponse>) :
    RecyclerView.Adapter<ClasificacionPadreAdapter.GrupoViewHolder>() {

    class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreGrupo: TextView = itemView.findViewById(R.id.textNombreGrupo)
        val recyclerClasificacion: RecyclerView = itemView.findViewById(R.id.recyclerClasificacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grupo_con_clasificacion, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = grupos[position]
        holder.nombreGrupo.text = grupo.grupo

        val adapter = ClasificacionAdapter(grupo.clasificaciones)
        holder.recyclerClasificacion.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerClasificacion.adapter = adapter
    }

    override fun getItemCount(): Int = grupos.size
}

