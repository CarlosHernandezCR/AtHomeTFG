package com.example.athometfgandroidcarloshernandez.data.model.response

import com.example.athometfgandroidcarloshernandez.data.model.UsuarioCasaDTO


data class PantallaEstadosResponseDTO(
    val estado: String = "",
    val colorEstado: String = "",
    val colorUsuario: String = "",
    val idCasa: Int = 0,
    val nombreCasa: String = "",
    val direccion: String = "",
    val codigoInvitacion: String = "",
    val usuariosCasa: List<UsuarioCasaDTO> = emptyList(),
    val estadosDisponibles: List<String> = emptyList(),
    val accessToken: String = "",
    val refreshToken: String = ""
)