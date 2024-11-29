package com.example.inhometfgandroidcarloshernandez.data.model.response

data class Evento (
    val id : Int,
    val tipo: String,
    val nombre: String,
    val votacion: String,
    val horaComienzo: String,
    val horaFin: String,
    val organizador: String,
)