// src/main/java/com/example/navarrafutbol/adapter/Adapter.kt
package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navarrafutbol.R
import com.example.navarrafutbol.model.Categoria
import com.example.navarrafutbol.model.Grupo
import com.example.navarrafutbol.model.Partido
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CategoriaAdapter(private val categorias: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvCat: TextView = view.findViewById(R.id.tvCategoria)
        val rvGrupos: RecyclerView = view.findViewById(R.id.rvGrupos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cat = categorias[position]
        holder.tvCat.text = cat.Categoria
        holder.rvGrupos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GrupoAdapter(cat.Grupos)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount() = categorias.size
}

class GrupoAdapter(private val grupos: List<Grupo>) :
    RecyclerView.Adapter<GrupoAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvGrupo: TextView = view.findViewById(R.id.tvGrupo)
        val rvPartidos: RecyclerView = view.findViewById(R.id.rvPartidos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grupo, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val grupo = grupos[position]
        holder.tvGrupo.text = grupo.Grupo
        holder.rvPartidos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PartidoAdapter(grupo.Partidos)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount() = grupos.size
}

class PartidoAdapter(private val items: List<Partido>) :
    RecyclerView.Adapter<PartidoAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val localName: TextView = view.findViewById(R.id.tv_equipo_local)
        val visitanteName: TextView = view.findViewById(R.id.tv_equipo_visitante)
        val hora: TextView = view.findViewById(R.id.tv_hora)
        val escudoLocal: ImageView = view.findViewById(R.id.iv_escudo_local)
        val escudoVisit: ImageView = view.findViewById(R.id.iv_escudo_visitante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_partido, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.localName.text = p.EquipoLocal
        holder.visitanteName.text = p.EquipoVisitante

        // Parseo seguro de la fecha
        holder.hora.text = try {
            val inFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dt = LocalDateTime.parse(p.Fecha, inFmt)
            dt.format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            "N/A"
        }

        Glide.with(holder.itemView)
            .load(p.EscudoLocalUrl)
            .placeholder(R.drawable.escudo)
            .error(R.drawable.escudo)
            .into(holder.escudoLocal)

        Glide.with(holder.itemView)
            .load(p.EscudoVisitanteUrl)
            .placeholder(R.drawable.escudo)
            .error(R.drawable.escudo)
            .into(holder.escudoVisit)

    }

    override fun getItemCount() = items.size
}
