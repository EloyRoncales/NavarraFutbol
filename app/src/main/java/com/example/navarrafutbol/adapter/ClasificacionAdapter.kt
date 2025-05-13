package com.example.navarrafutbol.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Clasificacion

class ClasificacionAdapter(private val lista: List<Clasificacion>) :
    RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder>() {

    class ClasificacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val equipo: TextView = view.findViewById(R.id.nombreEquipo)
        val detalles: TextView = view.findViewById(R.id.detallesEquipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClasificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clasificacion, parent, false)
        return ClasificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClasificacionViewHolder, position: Int) {
        val item = lista[position]
        val posicion = position + 1
        holder.equipo.text = "$posicion. ${item.equipo?.nombre ?: "Equipo ${item.equipoId}"}"
        holder.detalles.text = "Pts: ${item.puntos} | PJ: ${item.partidosJugados} | G: ${item.partidosGanados} | E: ${item.partidosEmpatados} | P: ${item.partidosPerdidos} | GF: ${item.golesFavor} | GC: ${item.golesContra}"

        when (position) {
            0 -> holder.itemView.setBackgroundColor(Color.parseColor("#D0F0C0")) // Verde claro
            in (itemCount - 3) until itemCount -> holder.itemView.setBackgroundColor(Color.parseColor("#F8D7DA")) // Rojo claro
            else -> holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun getItemCount() = lista.size
}
