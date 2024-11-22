package com.example.inhometfgandroidcarloshernandez.data.model.response

data class PantallaEstadosResponseDTO(
    val estado: String = "",
    val nombreCasa: String = "",
    val direccion: String = "",
    val usuariosCasa: List<UsuarioCasaResponseDTO> = emptyList(),
)