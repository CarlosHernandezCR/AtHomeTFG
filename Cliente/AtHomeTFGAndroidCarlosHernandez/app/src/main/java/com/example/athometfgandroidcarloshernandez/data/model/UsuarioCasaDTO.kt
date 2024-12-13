package com.example.athometfgandroidcarloshernandez.data.model

import com.example.athometfgandroidcarloshernandez.common.Constantes

data class UsuarioCasaDTO(
    val nombre: String = Constantes.NADA,
    val estado: String = Constantes.NADA,
    val colorEstado: String = Constantes.NADA,
    val colorUsuario: String = Constantes.NADA,
)