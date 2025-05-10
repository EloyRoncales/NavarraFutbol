package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Partido


class PartidoAdapter(private val partidos: List<Partido>) : RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partido, parent, false)
        return PartidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartidoViewHolder, position: Int) {
        val partido = partidos[position]
        holder.bind(partido)
    }

    override fun getItemCount(): Int = partidos.size

    inner class PartidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val equipoLocal: TextView = itemView.findViewById(R.id.tvEquipoLocal)
        private val fecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val equipoVisitante: TextView = itemView.findViewById(R.id.tvEquipoVisitante)

        fun bind(partido: Partido) {
            equipoLocal.text = partido.local.nombre
            equipoVisitante.text = partido.visitante.nombre
            fecha.text = partido.fecha
        }
    }
}


