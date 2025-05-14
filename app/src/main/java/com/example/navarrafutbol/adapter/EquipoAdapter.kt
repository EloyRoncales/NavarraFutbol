package com.example.navarrafutbol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navarrafutbol.databinding.ItemEquipoBinding
import com.example.navarrafutbol.model.EquipoFav

class EquipoAdapter(
    private val equipos: List<EquipoFav>,
    private val onItemClick: (EquipoFav) -> Unit
) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    inner class EquipoViewHolder(private val binding: ItemEquipoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipo: EquipoFav) {
            binding.textNombre.text = equipo.nombre
            binding.textEstadio.text = "Estadio: ${equipo.estadio}"
            Glide.with(binding.imageEscudo.context)
                .load(equipo.escudoUrl)
                .into(binding.imageEscudo)

            binding.root.setOnClickListener {
                onItemClick(equipo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val binding = ItemEquipoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        holder.bind(equipos[position])
    }

    override fun getItemCount(): Int = equipos.size
}
