package com.example.inhometfgandroidcarloshernandez.data.model

import com.example.inhometfgandroidcarloshernandez.common.Constantes

data class EventoDTO (
    val id : Int = 0,
    val tipo: String = Constantes.NADA,
    val nombre: String=Constantes.NADA,
    val votacion: String=Constantes.NADA,
    val horaComienzo: String=Constantes.NADA,
    val horaFin: String=Constantes.NADA,
    val organizador: String =Constantes.NADA,
)