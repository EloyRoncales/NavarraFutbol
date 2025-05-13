package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.ClasificacionItem

class ClasificacionAdapter(private val lista: List<ClasificacionItem>) :
    RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClasificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clasificacion, parent, false)
        return ClasificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClasificacionViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

    class ClasificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val equipoNombre: TextView = itemView.findViewById(R.id.textEquipoNombre)
        private val puntos: TextView = itemView.findViewById(R.id.textPuntos)
        private val partidos: TextView = itemView.findViewById(R.id.textPartidosJugados)
        private val escudo: ImageView = itemView.findViewById(R.id.imageEscudo)

        fun bind(item: ClasificacionItem) {
            equipoNombre.text = item.equipo.nombre
            puntos.text = "Pts: ${item.puntos}"
            partidos.text = "PJ: ${item.partidosJugados}"
            Glide.with(itemView.context).load(item.equipo.escudoUrl).into(escudo)
        }
    }
}
