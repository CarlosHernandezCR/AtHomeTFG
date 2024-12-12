package com.example.inhometfgandroidcarloshernandez.data.model.request

data class CambiarEstadoRequestDTO(
    val estado: String,
    val idCasa: String,
    val idUsuario: String
)