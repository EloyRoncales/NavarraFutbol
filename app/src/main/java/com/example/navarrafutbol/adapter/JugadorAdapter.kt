package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Jugador

class JugadorAdapter(private val jugadores: List<Jugador>) :
    RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder>() {

    class JugadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.textNombreJugador)
        private val posicion: TextView = itemView.findViewById(R.id.textPosicionJugador)
        private val edad: TextView = itemView.findViewById(R.id.textEdadJugador)
        private val goles: TextView = itemView.findViewById(R.id.textGolesJugador)

        fun bind(jugador: Jugador) {
            nombre.text = jugador.nombre
            posicion.text = "Posición: ${jugador.posicion}"
            edad.text = "Edad: ${jugador.edad} años"
            goles.text = "Goles: ${jugador.goles}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jugador, parent, false)
        return JugadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        holder.bind(jugadores[position])
    }

    override fun getItemCount(): Int = jugadores.size
}
