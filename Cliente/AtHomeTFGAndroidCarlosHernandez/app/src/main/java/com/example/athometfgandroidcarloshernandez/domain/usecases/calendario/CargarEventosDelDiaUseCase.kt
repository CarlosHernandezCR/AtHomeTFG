package com.example.athometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.athometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class CargarEventosDelDiaUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa:String,dia:Int,mes:Int,anio:Int) = eventosRepository.cargarEventosDelDia(idCasa,dia,mes,anio)
}