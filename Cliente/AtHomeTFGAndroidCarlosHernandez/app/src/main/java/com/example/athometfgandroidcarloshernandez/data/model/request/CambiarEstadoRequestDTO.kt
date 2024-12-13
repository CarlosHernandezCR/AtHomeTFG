package com.example.athometfgandroidcarloshernandez.data.model.request

data class CambiarEstadoRequestDTO(
    val estado: String,
    val idCasa: String,
    val idUsuario: String
)