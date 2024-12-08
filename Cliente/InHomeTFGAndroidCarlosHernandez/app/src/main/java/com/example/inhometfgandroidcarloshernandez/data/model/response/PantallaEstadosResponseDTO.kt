package com.example.inhometfgandroidcarloshernandez.data.model.response

import com.example.inhometfgandroidcarloshernandez.common.Constantes

data class PantallaEstadosResponseDTO(
    val estado: String = Constantes.NADA,
    val idCasa: Int? = null,
    val nombreCasa: String = Constantes.NADA,
    val direccion: String = Constantes.NADA,
    val usuariosCasa: List<UsuarioCasaResponseDTO> = emptyList(),
    val estadosDisponibles: List<String> = emptyList()
)