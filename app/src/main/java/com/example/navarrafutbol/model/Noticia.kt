package com.example.navarrafutbol.model

/**
 * Representa una noticia deportiva.
 *
 * @property id Identificador único de la noticia.
 * @property titulo Título principal de la noticia.
 * @property contenido Texto completo del cuerpo de la noticia.
 * @property autor Nombre del autor que escribió la noticia.
 * @property fechaPublicacion Fecha en la que fue publicada (formato ISO 8601).
 */
data class Noticia(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val fechaPublicacion: String
)
