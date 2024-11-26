package com.example.inhometfgandroidcarloshernandez.data.model.response

data class PantallaEstadosResponseDTO(
    val estado: String = "",
    val idCasa: Int = 0,
    val nombreCasa: String = "",
    val direccion: String = "",
    val usuariosCasa: List<UsuarioCasaResponseDTO> = emptyList(),
    val estadosDisponibles: List<String> = emptyList()
)