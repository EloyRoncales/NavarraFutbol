package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Noticia

class NoticiasAdapter(
    private val listaNoticias: List<Noticia>,
    private val onClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = listaNoticias[position]
        holder.titulo.text = noticia.titulo
        holder.autor.text = noticia.autor
        holder.fecha.text = noticia.fechaPublicacion
        holder.itemView.setOnClickListener {
            onClick(noticia)
        }
    }

    override fun getItemCount(): Int = listaNoticias.size

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.tituloNoticia)
        val autor: TextView = itemView.findViewById(R.id.autorNoticia)
        val fecha: TextView = itemView.findViewById(R.id.fechaNoticia)
    }
}

