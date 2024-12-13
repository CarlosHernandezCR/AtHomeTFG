package com.example.athometfgandroidcarloshernandez.data.model

import com.example.athometfgandroidcarloshernandez.common.Constantes

data class EventoDTO (
    val id : Int = 0,
    val tipo: String = Constantes.NADA,
    val nombre: String=Constantes.NADA,
    val votacion: String=Constantes.NADA,
    val horaComienzo: String=Constantes.NADA,
    val horaFin: String=Constantes.NADA,
    val organizador: String =Constantes.NADA,
)